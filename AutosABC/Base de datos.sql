

DROP TABLE IF EXISTS usuario CASCADE;

CREATE TABLE usuario(
	id_usuario VARCHAR(10) NOT NULL,
	password VARCHAR(16) NOT NULL,
	cedula VARCHAR(11),
	primer_nombre VARCHAR(20),
	segundo_nombre VARCHAR(20),
	primer_apellido VARCHAR(20),
	segundo_apellido VARCHAR(20),
	telefono1 INTEGER,   	
	telefono2 INTEGER,	
	direccion VARCHAR(20),	
	foto BYTEA,
	fecha_nacimiento DATE,
	estado VARCHAR(20),
	id_sede VARCHAR(10),
	tipo VARCHAR(8),
	CONSTRAINT usuario_pk PRIMARY KEY (id_usuario)
);


DROP TABLE IF EXISTS sede CASCADE;
INSERT INTO usuario(id_usuario, password, tipo) VALUES ('0001','0001','Gerente');
INSERT INTO usuario(id_usuario, password, tipo) VALUES ('0002','0002','Vendedor');
INSERT INTO usuario(id_usuario, password, tipo) VALUES ('0003','0003','Jefe');


CREATE TABLE sede(
	id_sede VARCHAR(10) NOT NULL,
	ciudad VARCHAR(20),
	direccion VARCHAR(30),
	telefono1 VARCHAR (10),
	telefono2 VARCHAR (10),
	id_gerente VARCHAR(10) NOT NULL,
	CONSTRAINT sede_pk PRIMARY KEY (id_sede)
);


ALTER TABLE usuario ADD	CONSTRAINT sede_fk FOREIGN KEY (id_sede) REFERENCES sede (id_sede);
ALTER TABLE sede ADD CONSTRAINT gerente_fk FOREIGN KEY (id_gerente) REFERENCES usuario(id_usuario);

DROP TABLE IF EXISTS orden_trabajo CASCADE;

CREATE TABLE orden_trabajo(
	id_orden VARCHAR(10) NOT NULL, 
	placa VARCHAR(7), 
	marca VARCHAR(15), 
	modelo VARCHAR(15), 
	descripcion VARCHAR(50), 
	precio INTEGER,
	id_jefe_taller VARCHAR(10) NOT NULL,
	CONSTRAINT orden_trabajo_pk PRIMARY KEY (id_orden),
	CONSTRAINT jefe_taller_fk FOREIGN KEY (id_jefe_taller) REFERENCES usuario (id_usuario)
);


DROP TABLE IF EXISTS repuesto CASCADE;

CREATE TABLE repuesto (
	id_repuesto VARCHAR (10) NOT NULL,
	tipo VARCHAR(20),
	marca VARCHAR(20),
	proveedor VARCHAR(20),
	cantidad INTEGER,
	descripcion VARCHAR(20),
	id_sede VARCHAR(10),
	CONSTRAINT repuesto_pk PRIMARY KEY (id_repuesto),
	CONSTRAINT sede_fk FOREIGN KEY  (id_sede) REFERENCES sede (id_sede)
);


DROP TABLE IF EXISTS repuestos_orden CASCADE;

CREATE TABLE repuestos_orden (
	id_repuesto VARCHAR(10) NOT NULL,
	id_orden VARCHAR(10) NOT NULL,
	cantidad INTEGER,
	CONSTRAINT repuestos_orden_pk PRIMARY KEY (id_repuesto, id_orden),
	CONSTRAINT repuesto_fk FOREIGN KEY (id_repuesto) REFERENCES repuesto (id_repuesto),
	CONSTRAINT orden_fk FOREIGN KEY (id_orden) REFERENCES orden_trabajo (id_orden)
);


DROP TABLE IF EXISTS carro CASCADE;

CREATE TABLE carro (
	id_carro VARCHAR (10) NOT NULL,
	marca VARCHAR (15),
	referencia VARCHAR (15),
	modelo VARCHAR (15),
	foto BYTEA,
	todo_terreno BOOLEAN,			
	precio INTEGER,
	peso INTEGER,
	cilindraje INTEGER,
	color VARCHAR(10),
	estado VARCHAR(10),
	id_sede VARCHAR(10),
	CONSTRAINT carro_pk PRIMARY KEY (id_carro),
	CONSTRAINT sede_fk FOREIGN KEY (id_sede) REFERENCES sede (id_sede)
);


DROP TABLE IF EXISTS manejo_inventarioCarros CASCADE;

CREATE TABLE manejo_inventarioCarros (
	id_vendedor VARCHAR(10) NOT NULL,
	id_carro VARCHAR(10) NOT NULL,
	fecha_hora TIMESTAMP,
	tipo_moviento VARCHAR(8), --INGRESO o EGRESO
	CONSTRAINT manejo_inventarioCarros_pk PRIMARY KEY (id_vendedor, id_carro, fecha_hora),
	CONSTRAINT id_vendedor_fk FOREIGN KEY (id_vendedor) REFERENCES usuario (id_usuario),
	CONSTRAINT id_carro_fk FOREIGN KEY (id_carro) REFERENCES carro (id_carro)
);


DROP TABLE IF EXISTS manejo_inventarioRepuestos CASCADE;

CREATE TABLE manejo_inventarioRepuestos (
	id_jefe_taller VARCHAR(10) NOT NULL,
	id_repuesto VARCHAR(10) NOT NULL,
	fecha_hora TIMESTAMP,
	tipo_moviento VARCHAR(8),
	cantidad INTEGER,
	CONSTRAINT manejo_inventarioRepuestos_pk PRIMARY KEY (id_jefe_taller, id_repuesto, fecha_hora),
	CONSTRAINT id_jefe_taller_fk FOREIGN KEY (id_jefe_taller) REFERENCES usuario (id_usuario),
	CONSTRAINT id_repuesto_fk FOREIGN KEY (id_repuesto) REFERENCES repuesto (id_repuesto)
);


DROP TABLE IF EXISTS cliente CASCADE;

CREATE TABLE cliente(
	cedula VARCHAR(15) NOT NULL,
	primer_nombre VARCHAR(20),
	segundo_nombre VARCHAR(20),
	primer_apellido VARCHAR(20),
	segundo_apellido VARCHAR(20),
	telefono VARCHAR(10),
	CONSTRAINT cliente_pk PRIMARY KEY (cedula)
);


DROP TABLE IF EXISTS cotizacion CASCADE;

CREATE TABLE cotizacion (
	id_cotizacion VARCHAR(10) NOT NULL,
	fecha DATE,
	cedula_cliente VARCHAR (15),
	valor INTEGER,
	id_vendedor VARCHAR(20),
	CONSTRAINT cotizacion_pk PRIMARY KEY (id_cotizacion),
	CONSTRAINT cedula_fk FOREIGN KEY (cedula_cliente) REFERENCES cliente (cedula),
	CONSTRAINT id_vendedor_fk FOREIGN KEY (id_vendedor) REFERENCES usuario (id_usuario)
);


DROP TABLE IF EXISTS carros_por_cotizacion CASCADE;

CREATE TABLE carros_por_cotizacion (
	id_cotizacion VARCHAR(10) NOT NULL,
	id_carro VARCHAR (10) NOT NULL,
	CONSTRAINT carros_por_cotizacion_pk PRIMARY KEY (id_cotizacion, id_carro)
);


DROP TABLE IF EXISTS venta CASCADE;

CREATE TABLE venta(
	id_venta VARCHAR (10) NOT NULL,
	id_cotizacion VARCHAR (10) NOT NULL,
	descuentos INTEGER,
	precio_total INTEGER,
	forma_pago VARCHAR (20),
	CONSTRAINT venta_pk PRIMARY KEY (id_venta),
	CONSTRAINT id_cotizacion_fk FOREIGN KEY (id_cotizacion) REFERENCES cotizacion (id_cotizacion) 
);

