USE SuperKinal;

DELIMITER $$

CREATE FUNCTION fn_CalcularPromociones(proId INT) RETURNS DECIMAL(10,2) DETERMINISTIC
BEGIN
    DECLARE resultado INT DEFAULT 0;
    DECLARE i INT DEFAULT 1;
    DECLARE fecFin DATE;

    SET resultado = 0; 

    resultadoLoop: LOOP
        SELECT fechaFinalizacion INTO fecFin FROM Promociones
            WHERE promocionId = i AND productoId = proId;

        IF fecFin IS NOT NULL THEN
            IF fecFin > DATE(NOW()) THEN
                SET resultado = 1; 
            END IF;
        END IF;

        SET i = i + 1; 

        IF i > (SELECT COUNT(*) FROM Promociones WHERE productoId = proId) THEN
            LEAVE resultadoLoop; 
        END IF;
    END LOOP resultadoLoop;

    RETURN resultado;
END$$

DELIMITER ;

DELIMITER $$

CREATE FUNCTION fn_CalcularTotalFacturas(facId INT) RETURNS DECIMAL(10,2) DETERMINISTIC
BEGIN
    DECLARE total DECIMAL(10,2) DEFAULT 0.0;
    DECLARE i INT DEFAULT 1;
    DECLARE precio DECIMAL(10,2);

    totalLoop: LOOP
        IF fn_CalcularPromociones(facId) = 0 THEN
            IF facId = (SELECT facturaId FROM DetalleFacturas DF WHERE detalleFacturaId = i) THEN
                SET precio = (SELECT PD.precioVentaUnitario FROM Productos PD WHERE productoId = (SELECT productoId FROM DetalleFacturas WHERE detalleFacturaId = i));
                SET total = total + precio + (precio * 0.12);
            END IF;
        ELSE 
            IF facId = (SELECT facturaId FROM DetalleFactura DF WHERE detalleFacturaId = i) THEN
                SET precio = (SELECT PM.precioPromocion FROM Promociones PM WHERE productoId = (SELECT productoId FROM DetalleFacturas WHERE detalleFacturaId = i));
                SET total = total + precio + (precio * 0.12);
            END IF;
        END IF;

        IF i = (SELECT COUNT(*) FROM DetalleFacturas) THEN
            LEAVE totalLoop;
        END IF;

        SET i = i + 1;
    END LOOP totalLoop;

    CALL sp_AsignarTotalFacturas(facId, tot);

    RETURN total;
END $$

DELIMITER ;

DELIMITER $$

CREATE FUNCTION fn_DesaumentarStocks(proId INT) RETURNS INT DETERMINISTIC
BEGIN
    DECLARE cantSto INT DEFAULT 0;
    DECLARE cantidadComprada INT DEFAULT 0;

    SELECT cantidadStock INTO cantidadComprada FROM Productos WHERE productoId = proId;
    
    SET cantSto = cantidadComprada - 1;
    
    CALL sp_EliminarStocks(proId, cantSto);
    
    RETURN cantSto;
END $$

DELIMITER ;

DELIMITER $$

CREATE TRIGGER tg_TotalFacturas
AFTER INSERT ON DetalleFacturas
FOR EACH ROW
BEGIN
    DECLARE tot DECIMAL(10,2);
    DECLARE cantSto INT;
    
    SET tot = fn_CalcularTotalFacturas(NEW.facturaId);
    SET cantSto = fn_DesaunmentarStocks(NEW.productoId); 
END$$

DELIMITER ;

DELIMITER $$

CREATE FUNCTION fn_CalcularTotalCompra(comId INT) RETURNS DECIMAL(10,2) DETERMINISTIC
BEGIN
    DECLARE totalCom DECIMAL(10,2) DEFAULT 0.0;
    DECLARE i INT DEFAULT 1;
    DECLARE precio DECIMAL(10,2);
    DECLARE cantidadComprada INT DEFAULT 0;
    DECLARE cantCompra INT;
    DECLARE cursorProductoId INT; 
    DECLARE cursorCompraId INT;
    
    DECLARE cursorDetalleCompras CURSOR FOR
        SELECT DC.cantidadCompra, DC.productoId, DC.compraId FROM DetalleCompras DC
    ;
    
    OPEN cursorDetalleCompras;
    
    totalLoop: LOOP
        FETCH cursorDetalleCompras INTO cantCompra, cursorProductoId, cursorCompraId;
        
        IF comId = cursorCompraId THEN
            SET precio = (SELECT P.precioCompra FROM Productos P WHERE P.productoId = cursorProductoId);
            SET cantidadComprada = cantCompra;
            SET totalCom = totalCom + (precio * cantidadComprada + (cantidadComprada * precio * 0.12));
        END IF;
        
        IF i = (SELECT COUNT(*) FROM DetalleCompras) THEN
            LEAVE totalLoop;
        END IF;
        
        SET i = i + 1;
    END LOOP totalLoop;
    
    CALL sp_AsignarTotalCompras(comId, totalCom);
    
    RETURN totalCom;
END $$

DELIMITER ;

DELIMITER $$

CREATE FUNCTION fn_AumentarStocks(proId INT) RETURNS INT DETERMINISTIC
BEGIN
    DECLARE cantSto INT DEFAULT 0;
    DECLARE cantidadComprada INT DEFAULT 0;
    DECLARE cantidad INT DEFAULT 0;
    
    SELECT cantidadStock INTO cantidad FROM Productos WHERE productoId = proId LIMIT 1;
    SELECT cantidadCompra INTO cantidadComprada FROM DetalleCompras WHERE productoId = proId LIMIT 1;
    
	SET cantSto = cantSto + cantidadComprada + cantidad;

	CALL sp_AgregarStocks(proId, cantSto);

	RETURN cantSto;
END $$

DELIMITER ;

DELIMITER $$
CREATE TRIGGER tg_TotalCompras
AFTER INSERT ON DetalleCompras
FOR EACH ROW
BEGIN
    DECLARE totCom DECIMAL(10,2);
    DECLARE cantSto INT;
    
    SET totCom = fn_CalcularTotalCompra(new.compraId);
    SET cantSto = fn_AumentarStocks(new.productoId); 
END $$
DELIMITER ;


set global time_zone = '-6:00';