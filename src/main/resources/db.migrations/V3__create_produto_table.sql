create table produto(
    id UUID primary key default gen_random_uuid(),
    nome varchar(50) not null,
    descricao varchar(300) null,
    preco DECIMAL(10,2) not null,
    quantidade_estoque INTEGER not null,
    img_url varchar(255) null,
    criado_em timestamp not null default CURRENT_TIMESTAMP,
    atualizado_em timestamp not null default CURRENT_TIMESTAMP,
    id_vendedor UUID

    CONSTRAINT fk_vendedor
    FOREIGN KEY id_vendedor
    REFERENCES usuario(id)
    ON DELETE CASCADE ALL
);