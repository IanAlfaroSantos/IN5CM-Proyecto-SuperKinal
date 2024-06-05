USE SuperKinal;

DELIMITER $$
CREATE FUNCTION fn_CalcularPromocion(proId INT) RETURNS DECIMAL(10,2) DETERMINISTIC
BEGIN
    DECLARE resultado INT DEFAULT 0;
    DECLARE i INT DEFAULT 1;
    DECLARE feFi DATE;

    SET resultado = 0; 
    
    resultadoLoop: LOOP
        SELECT fechaFinalizacion INTO feFi FROM Promociones
        WHERE promocionId = i AND productoId = proId;

        IF feFi IS NOT NULL THEN
            IF feFi > DATE(NOW()) THEN
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
CREATE FUNCTION fn_totalFactura(facId INT) RETURNS DECIMAL(10,2) DETERMINISTIC
BEGIN
    DECLARE total DECIMAL(10,2) DEFAULT 0.0;
    DECLARE i INT DEFAULT 1;
    DECLARE precio DECIMAL(10,2);
    DECLARE curCantidadCompra INT;
    DECLARE curProductoId INT;

    totalLoop: LOOP
        IF fn_CalcularPromocion(facId) = 0 THEN
            IF facId = (SELECT facturaId FROM DetalleFacturas DF WHERE detalleFacturaId = i) THEN
                SET precio = (SELECT PD.precioVentaUnitario FROM Productos PD WHERE productoId = (SELECT productoId FROM DetalleFacturas WHERE detalleFacturaId = i));
                SET total = total + precio + (precio*0.12);
            END IF;
        ELSE 
            IF facId = (SELECT facturaId FROM DetalleFacturas DF WHERE detalleFacturaId = i) THEN
                SET precio = (SELECT PR.precioPromocion FROM Promociones PR WHERE productoId = (SELECT productoId FROM DetalleFacturas WHERE detalleFacturaId = i));
                SET total = total + precio + (precio*0.12);
            END IF;
        END IF;

        IF i = (SELECT COUNT(*) FROM DetalleFacturas) THEN
            LEAVE totalLoop;
        END IF;

        SET i = i + 1;
    END LOOP totalLoop;

    CALL sp_asignarTotalFactura(facId, total);

    RETURN total;
END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER tg_totalFactura
AFTER INSERT ON DetalleFacturas
FOR EACH ROW
BEGIN
    DECLARE tot DECIMAL(10,2);
    DECLARE canCom INT;
    
    SET tot = fn_totalFactura(new.facturaId);
    SET canCom = fn_eliminarStockProducto(new.productoId); 
END$$
DELIMITER ;

DELIMITER $$
CREATE FUNCTION fn_PrecioTotalCompras(comId INT) RETURNS DECIMAL(10,2) DETERMINISTIC
BEGIN
    DECLARE totCom DECIMAL(10,2) DEFAULT 0.0;
    DECLARE i INT DEFAULT 1;
    DECLARE precio DECIMAL(10,2);
    DECLARE cantidadComprada INT DEFAULT 0;
    DECLARE curCantidadCompra INT; 
    DECLARE curProductoId INT; 
    DECLARE curCompraId INT;
    
    DECLARE curDetalleCompra CURSOR FOR
        SELECT DC.cantidadCompra, DC.productoId, DC.compraId FROM DetalleCompras DC;

    OPEN curDetalleCompra;
    
    totalLoop: LOOP
        FETCH curDetalleCompra INTO curCantidadCompra, curProductoId, curCompraId;
        
        IF comId = curCompraId THEN
            SET precio = (SELECT PD.precioCompra FROM Productos PD WHERE PD.productoId = curProductoId);
            SET cantidadComprada = curCantidadCompra;
            SET totCom = totCom + (precio * cantidadComprada + (cantidadComprada * precio * 0.12));
        END IF;
        
        IF i = (SELECT COUNT(*) FROM DetalleCompras) THEN
            LEAVE totalLoop;
        END IF;
        
        SET i = i + 1;
    END LOOP totalLoop;
    
    CALL sp_asignarTotalCompra(comId, totCom);
    
    RETURN totCom;
END $$
DELIMITER ;


DELIMITER $$
CREATE FUNCTION fn_IncrementarStockProducto(proId INT) RETURNS INT DETERMINISTIC
BEGIN
    DECLARE canCom INT DEFAULT 0;
    DECLARE cantidadComprada INT DEFAULT 0;
    DECLARE cantidad INT DEFAULT 0;
    
    SELECT cantidadStock INTO cantidad FROM Productos WHERE productoId = proId LIMIT 1;
    SELECT cantidadCompra INTO cantidadComprada FROM DetalleCompras WHERE productoId = proId LIMIT 1;
    
    SET canCom = canCom + cantidadComprada + cantidad;
    
    CALL sp_modificarStockCompra(proId, canCom);
    
    RETURN canCom;
END $$
DELIMITER ;

DELIMITER $$
CREATE FUNCTION fn_eliminarStockProducto(proId INT) RETURNS INT DETERMINISTIC
BEGIN
    DECLARE canCom INT DEFAULT 0;
    DECLARE cantidadComprada INT DEFAULT 0;

    SELECT cantidadStock INTO cantidadComprada FROM Productos WHERE productoId = proId;
    
    SET canCom = cantidadComprada - 1;
    
    CALL sp_modificarStock(proId, canCom);
    
    RETURN canCom;
END $$
DELIMITER ;


DELIMITER $$
CREATE TRIGGER tg_totalCompras
AFTER INSERT ON DetalleCompras
FOR EACH ROW
BEGIN
    DECLARE totCom DECIMAL(10,2);
    DECLARE canCom INT;
    
    SET totCom = fn_PrecioTotalCompras(new.compraId);
    SET canCom = fn_IncrementarStockProducto(new.productoId); 
END$$
DELIMITER ;

SELECT * FROM DetalleFacturas
JOIN Facturas ON DetalleFacturas.facturaId = Facturas.facturaId
JOIN Clientes ON Facturas.clienteId = Clientes.clienteId
JOIN Productos ON DetalleFacturas.productoId = Productos.productoId
WHERE Facturas.facturaId = 1;

set global time_zone = '-6:00';