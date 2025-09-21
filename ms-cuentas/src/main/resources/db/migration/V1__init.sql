create table cuenta
(
    id            bigserial primary key,
    numero_cuenta varchar(32)    not null unique,
    tipo          varchar(20)    not null,
    saldo_inicial numeric(18, 2) not null,
    estado        boolean        not null,
    cliente_id    uuid           not null
);

create table movimiento
(
    id              bigserial primary key,
    fecha           timestamp      not null,
    tipo            varchar(20)    not null,
    valor           numeric(18, 2) not null,
    saldo_posterior numeric(18, 2) not null,
    numero_cuenta   varchar(32)    not null references cuenta (numero_cuenta)
);

create table idempotency
(
    key        varchar(80) primary key,
    created_at timestamp not null default now()
);
