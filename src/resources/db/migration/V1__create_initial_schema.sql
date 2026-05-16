create table users(
    id bigserial primary key,
    email varchar(255) unique not null,
    password varchar(255)
);

create table user_role(
    user_id integer,
    role_name varchar(255) not null unique,
    constraint fk_user_id foreign key (user_id) references users(id)
);