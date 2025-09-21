create table if not exists cliente_cache
(
    cliente_id uuid primary key,
    nombre     varchar(120),
    estado     boolean   not null,
    updated_at timestamp not null default now()
);