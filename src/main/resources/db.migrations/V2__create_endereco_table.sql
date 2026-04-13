create table endereco(
    id UUID primary key default gen_random_uuid(),
    cep varchar(255) not null,
    rua varchar(255) null,
    cidade varchar(255) null,
    estado varchar(255) null,
    bairro varchar(255) null,
    complemento varchar(255) null,
    numeroCasa varchar(255) null,
    tipo_endereco varchar(255) not null,
    id_cliente UUID

    CONSTRAINT fk_cliente FOREIGN KEY(id_cliente) REFERENCES usuario(id) ON DELETE CASCADE ALL
);