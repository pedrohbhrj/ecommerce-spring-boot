create table usuario(
    id UUID primary key default gen_random_uuid(),
    nome_completo varchar(255) null,
    email varchar(255) unique not null,
    senha varchar(255) not null,
    cpf varchar(255) unique null,
    data_nascimento date null,
    role varchar(30) not null,
    criado_em timestamp not null not update,
    atualizado_em timestamp not null
);