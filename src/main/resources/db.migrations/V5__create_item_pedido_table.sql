create table item_pedido(
    id UUID primary key gen_random_uuid(),
    preco_total_produto DECIMAL(10,2) not null,
    qtd_produto INTEGER not null,
    criado_em timestamp not null default CURRENT_TIMESTAMP,
    atualizado_em timestamp not null default CURRENT_TIMESTAMP,
    id_produto UUID,
    id_pedido UUID

    CONSTRAINT fk_produto
    FOREIGN KEY id_produto
    REFERENCES produto(id)
    ON DELETE CASCADE ALL,
    CONSTRAINT fk_pedido
    FOREIGN KEY id_pedido
    REFERENCES pedido(id)
    ON DELETE CASCADE ALL
);