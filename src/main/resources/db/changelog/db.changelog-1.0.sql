--liquibase formatted sql

--changeset pozitiv:1
CREATE TABLE IF NOT EXISTS users (
                       id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                       name VARCHAR(255),
                       login VARCHAR(255) UNIQUE NOT NULL,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       city VARCHAR(255),
                       last_connect TIMESTAMP,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(50),
                       registered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       phone VARCHAR(20)
);
--rollback DROP TABLE users;

--changeset pozitiv:2
CREATE TABLE IF NOT EXISTS products (
                          id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          description TEXT,
                          quantity BIGINT NOT NULL,
                          price NUMERIC(18,5) NOT NULL
);
--rollback DROP TABLE products;

--changeset pozitiv:3
CREATE TABLE IF NOT EXISTS orders (
                        id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                        user_id BIGINT NOT NULL,
                        product_id BIGINT NOT NULL,
                        order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        status VARCHAR(50),
                        quantity NUMERIC NOT NULL,
                        CONSTRAINT fk_orders_users FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                        CONSTRAINT fk_orders_products FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);
--rollback DROP TABLE orders;