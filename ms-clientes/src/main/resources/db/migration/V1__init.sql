create table persona
(
    id             bigserial primary key,
    nombre         varchar(120)       not null,
    genero         varchar(10),
    edad           int,
    identificacion varchar(50) unique not null,
    direccion      varchar(200),
    telefono       varchar(50)
);

create table cliente
(
    id         bigserial primary key,
    cliente_id uuid         not null unique,
    persona_id bigint       not null references persona (id),
    password   varchar(120) not null,
    estado     boolean      not null
);
