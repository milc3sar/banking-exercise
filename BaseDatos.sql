-- =============================================
--      ESQUEMA PARA EL MICROSERVICIO CLIENTES (ms-clientes)
-- =============================================

-- Tabla para almacenar la información de las personas
CREATE TABLE persona
(
    id             BIGSERIAL PRIMARY KEY,
    nombre         VARCHAR(120)       NOT NULL,
    genero         VARCHAR(10),
    edad           INT,
    identificacion VARCHAR(50) UNIQUE NOT NULL,
    direccion      VARCHAR(200),
    telefono       VARCHAR(50)
);

-- Tabla para almacenar la información de los clientes, vinculada a una persona
CREATE TABLE cliente
(
    id         BIGSERIAL PRIMARY KEY,
    cliente_id UUID         NOT NULL UNIQUE,
    persona_id BIGINT       NOT NULL REFERENCES persona (id),
    password   VARCHAR(120) NOT NULL,
    estado     BOOLEAN      NOT NULL
);


-- =============================================
--      ESQUEMA PARA EL MICROSERVICIO CUENTAS (ms-cuentas)
-- =============================================

-- Tabla para las cuentas bancarias de los clientes
CREATE TABLE cuenta
(
    id            BIGSERIAL PRIMARY KEY,
    numero_cuenta VARCHAR(32)    NOT NULL UNIQUE,
    tipo          VARCHAR(20)    NOT NULL,
    saldo_inicial NUMERIC(18, 2) NOT NULL,
    estado        BOOLEAN        NOT NULL,
    cliente_id    UUID           NOT NULL
);

-- Tabla para registrar los movimientos de las cuentas
CREATE TABLE movimiento
(
    id              BIGSERIAL PRIMARY KEY,
    fecha           TIMESTAMP      NOT NULL,
    tipo            VARCHAR(20)    NOT NULL,
    valor           NUMERIC(18, 2) NOT NULL,
    saldo_posterior NUMERIC(18, 2) NOT NULL,
    numero_cuenta   VARCHAR(32)    NOT NULL REFERENCES cuenta (numero_cuenta)
);

-- Tabla para garantizar la idempotencia en las operaciones (ej. evitar movimientos duplicados)
CREATE TABLE idempotency
(
    key        VARCHAR(80) PRIMARY KEY,
    created_at TIMESTAMP NOT NULL DEFAULT now()
);

-- Tabla para almacenar una caché de la información de clientes (para mejorar rendimiento y resiliencia)
CREATE TABLE IF NOT EXISTS cliente_cache
(
    cliente_id UUID PRIMARY KEY,
    nombre     VARCHAR(120),
    estado     BOOLEAN   NOT NULL,
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);