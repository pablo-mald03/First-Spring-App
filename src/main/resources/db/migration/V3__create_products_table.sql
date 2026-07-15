CREATE SEQUENCE IF NOT EXISTS products_id_seq
    START WITH 1
    INCREMENT BY 1
    CACHE 50;

CREATE TABLE products
(
    id BIGINT NOT NULL DEFAULT nextval('products_id_seq'),
    name VARCHAR(255) NOT NULL,
    description VARCHAR(500),
    price DOUBLE PRECISION NOT NULL,
    image VARCHAR(255),

    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,

    product_detail_id BIGINT,

    CONSTRAINT pk_products
        PRIMARY KEY (id),

    CONSTRAINT fk_product_detail
        FOREIGN KEY (product_detail_id)
            REFERENCES product_details(id)
            ON DELETE CASCADE
);