CREATE TABLE BEER_ORDER_SERVICE.customers (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    version BIGINT,
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    customer_name VARCHAR(255),
    api_key VARCHAR(36)
);

CREATE TABLE BEER_ORDER_SERVICE.beer_orders (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    version BIGINT,
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    customer_ref VARCHAR(255),
    customer_id VARCHAR(36) NOT NULL,
    order_status VARCHAR(50) DEFAULT 'NEW',
    order_status_callback_url VARCHAR(255),
    CONSTRAINT fk_beer_order_customer FOREIGN KEY (customer_id)
        REFERENCES customers(id) ON DELETE CASCADE
);

CREATE TABLE BEER_ORDER_SERVICE.beer_order_lines (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    version BIGINT,
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    beer_order_id VARCHAR(36) NOT NULL,
    beer_id VARCHAR(36),
    upc VARCHAR(255),
    order_quantity INT DEFAULT 0,
    quantity_allocated INT DEFAULT 0,
    CONSTRAINT fk_beer_order_line_order FOREIGN KEY (beer_order_id)
        REFERENCES beer_orders(id) ON DELETE CASCADE
);
