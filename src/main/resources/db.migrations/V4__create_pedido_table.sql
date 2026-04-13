create table pedido(
    id UUID primary key gen_random_uuid(),
    preco_total DECIMAL(10,2) not null,
    status_pedido varchar(30) not null,
    criado_em timestamp not null default CURRENT_TIMESTAMP,
    atualizado_em timestamp not null default CURRENT_TIMESTAMP,
    id_cliente UUID

    CONSTRAINT fk_cliente
    FOREIGN KEY id_cliente
    REFERENCES usuario(id)
    ON DELETE CASCADE ALL
);