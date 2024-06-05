-- DROP DATABASE IF EXISTS SuperKinal;

CREATE DATABASE IF NOT EXISTS SuperKinal;

USE SuperKinal;

-- ********************************** TABLAS ********************************** --

CREATE TABLE Clientes(
	clienteId INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(30) NOT NULL,
    apellido VARCHAR(30) NOT NULL,
    telefono VARCHAR(15),
    direccion VARCHAR(150) NOT NULL,
    nit VARCHAR(15),
    PRIMARY KEY PK_clienteId(clienteId)
);

CREATE TABLE Cargos(
	cargoId INT NOT NULL AUTO_INCREMENT,
    nombreCargo VARCHAR(30) NOT NULL,
    descripcionCargo VARCHAR(100) NOT NULL,
    PRIMARY KEY PK_cargoId(cargoId)
);

CREATE TABLE Empleados(
	empleadoId INT NOT NULL AUTO_INCREMENT,
    nombreEmpleado VARCHAR(30) NOT NULL,
    apellidoEmpleado VARCHAR(30) NOT NULL,
    sueldo DECIMAL(10,2) NOT NULL,
    horaEntrada TIME NOT NULL,
    horaSalida TIME NOT NULL,
    cargoId INT NOT NULL,
    encargadoId INT,
    PRIMARY KEY PK_empleadoId(empleadoId),
    CONSTRAINT FK_Empleados_Cargos FOREIGN KEY Empleados(cargoId)
        REFERENCES Cargos(cargoId),
    CONSTRAINT FK_encargadoId_Empleados FOREIGN KEY (encargadoId)
        REFERENCES Empleados(empleadoId)
);

CREATE TABLE Facturas(
	facturaId INT NOT NULL AUTO_INCREMENT,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    clienteId INT NOT NULL,
    empleadoId INT NOT NULL,
    total DECIMAL(10,2),
    PRIMARY KEY PK_facturaId(facturaId),
    CONSTRAINT FK_Facturas_Clientes FOREIGN KEY Facturas(clienteId)
		REFERENCES Clientes(clienteId),
	CONSTRAINT FK_Facturas_Empleados FOREIGN KEY Facturas(empleadoId)
		REFERENCES Empleados(empleadoId)
);

CREATE TABLE TicketSoportes(
	ticketSoporteId INT NOT NULL AUTO_INCREMENT,
    descripcionTicket VARCHAR(250) NOT NULL,
    estatus VARCHAR(30) NOT NULL,
    clienteId INT NOT NULL,
    facturaId INT,
    PRIMARY KEY PK_ticketSoporteId(ticketSoporteId),
    CONSTRAINT FK_TicketSoportes_Clientes FOREIGN KEY TicketSoportes(clienteId)
		REFERENCES Clientes(clienteId),
	CONSTRAINT FK_TicketSoportes_Facturas FOREIGN KEY TicketSoportes(facturaId)
		REFERENCES Facturas(facturaId)
);

CREATE TABLE Distribuidores(
	distribuidorId INT NOT NULL AUTO_INCREMENT,
    nombreDistribuidor VARCHAR(30) NOT NULL,
    direccionDistribuidor VARCHAR(200) NOT NULL,
    nitDistribuidor VARCHAR(15) NOT NULL,
    telefonoDistribuidor VARCHAR(15) NOT NULL,
    web VARCHAR(50),
    PRIMARY KEY PK_distribuidorId(distribuidorId)
);

CREATE TABLE CategoriaProductos(
	categoriaProductoId INT NOT NULL AUTO_INCREMENT,
    nombreCategoria VARCHAR(30) NOT NULL,
    descripcionCategoria VARCHAR(100) NOT NULL,
    PRIMARY KEY PK_categoriaProductoId(categoriaProductoId)
);

CREATE TABLE Productos(
	productoId INT NOT NULL AUTO_INCREMENT,
    nombreProducto VARCHAR(50) NOT NULL,
    descripcionProducto VARCHAR(100),
    cantidadStock INT NOT NULL,
    precioVentaUnitario DECIMAL(10,2) NOT NULL,
    precioVentaMayor DECIMAL(10,2) NOT NULL,
    precioCompra DECIMAL(10,2) NOT NULL,
    imagenProducto LONGBLOB,
    distribuidorId INT NOT NULL,
    categoriaProductoId INT NOT NULL,
    PRIMARY KEY PK_productoId(productoId),
    CONSTRAINT FK_Productos_Distribuidores FOREIGN KEY Productos(distribuidorId)
		REFERENCES Distribuidores(distribuidorId),
	CONSTRAINT FK_Productos_CategoriaProductos FOREIGN KEY Productos(categoriaProductoId)
		REFERENCES CategoriaProductos(categoriaProductoId)
);

CREATE TABLE Promociones(
	promocionId INT NOT NULL AUTO_INCREMENT,
    precioPromocion DECIMAL(10,2) NOT NULL,
    descripcionPromocion VARCHAR(100),
    fechaInicio DATE NOT NULL,
    fechaFinalizacion DATE NOT NULL,
    productoId INT NOT NULL,
    PRIMARY KEY PK_promocionId(promocionId),
    CONSTRAINT FK_Promociones_Productos FOREIGN KEY Promociones(productoId)
		REFERENCES Productos(productoId)
);

CREATE TABLE DetalleFacturas(
	detalleFacturaId INT NOT NULL AUTO_INCREMENT,
    facturaId INT NOT NULL,
    productoId INT NOT NULL,
    PRIMARY KEY PK_detalleFacturaId(detalleFacturaId),
    CONSTRAINT FK_DetalleFacturas_Facturas FOREIGN KEY DetalleFacturas(facturaId)
		REFERENCES Facturas(facturaId),
	CONSTRAINT FK_DetalleFacturas_Productos FOREIGN KEY DetalleFacturas(productoId)
		REFERENCES Productos(productoId) 
);

CREATE TABLE Compras(
	compraId INT NOT NULL AUTO_INCREMENT,
    fechaCompra DATE NOT NULL,
    totalCompra DECIMAL(10,2),
    PRIMARY KEY PK_compraId(compraId)
);

CREATE TABLE DetalleCompras(
	detalleCompraId INT NOT NULL AUTO_INCREMENT,
    cantidadCompra INT NOT NULL,
    productoId INT NOT NULL,
    compraId INT NOT NULL,
    PRIMARY KEY PK_detalleCompraId(detalleCompraId),
    CONSTRAINT FK_DetalleCompras_Productos FOREIGN KEY DetalleCompras(productoId)
		REFERENCES Productos(productoId),
	CONSTRAINT FK_DetalleCompras FOREIGN KEY DetalleCompras(compraId)
		REFERENCES Compras(compraId)
);

-- ************************ NIVELES DE ACCESO ************************ --

CREATE TABLE NivelesAccesos(
	nivelAccesoId INT NOT NULL AUTO_INCREMENT,
	nivelAcceso VARCHAR(40) NOT NULL,
    PRIMARY KEY PK_nivelAccesoId(nivelAccesoId)
);

-- ************************ USUARIOS ************************ --

CREATE TABLE Usuarios(
	usuarioId INT NOT NULL AUTO_INCREMENT,
    usuario VARCHAR(30) NOT NULL,
    contrasenia VARCHAR(30) NOT NULL,
    nivelAccesoId INT NOT NULL,
    empleadoId INT NOT NULL,
    PRIMARY KEY PK_usuarioId(usuarioId),
    CONSTRAINT FK_Usuarios_NivelesAccesos FOREIGN KEY Usuarios(nivelAccesoId)
		REFERENCES NivelesAccesos(nivelAccesoId),
	CONSTRAINT FK_Usuarios_Empleados FOREIGN KEY Usuarios(empleadoId)
		REFERENCES Empleados(empleadoId)
);

set global time_zone = '-6:00';