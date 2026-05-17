

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE user_role (
    user_id BIGINT NOT NULL REFERENCES users(id),
    role_name VARCHAR(50) NOT NULL
);

CREATE TABLE address (
    id BIGSERIAL PRIMARY KEY,
    street VARCHAR(255) NOT NULL,
    number VARCHAR(20) NOT NULL,
    zip_code VARCHAR(20) NOT NULL,
    complement VARCHAR(255),
    neighborhood VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    state VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL REFERENCES users(id)
);

CREATE TABLE category (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(120) NOT NULL UNIQUE,
    parent_id BIGINT REFERENCES category(id),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE product (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(120) NOT NULL,
    description TEXT,
    price NUMERIC(19,2) NOT NULL,
    stock_quantity INTEGER NOT NULL,
    img_url VARCHAR(255),
    category_id BIGINT REFERENCES category(id),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    total NUMERIC(19,2),
    order_status VARCHAR(30) NOT NULL,
    user_id BIGINT NOT NULL REFERENCES users(id),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE order_item (
    id BIGSERIAL PRIMARY KEY,
    quantity INTEGER NOT NULL,
    unit_price NUMERIC(19,2) NOT NULL,
    sub_total NUMERIC(19,2) NOT NULL,
    product_id BIGINT NOT NULL REFERENCES product(id),
    order_id BIGINT NOT NULL REFERENCES orders(id),
    created_at TIMESTAMP
);

CREATE TABLE payment (
    id BIGSERIAL PRIMARY KEY,
    transaction_id VARCHAR(255) UNIQUE,
    payment_status VARCHAR(20) NOT NULL,
    amount NUMERIC(19,2),
    order_id BIGINT NOT NULL REFERENCES orders(id),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);