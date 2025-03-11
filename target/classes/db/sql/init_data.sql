INSERT INTO BEER_ORDER_SERVICE.customers (id, version, create_date, last_modified_date, customer_name, api_key)
VALUES (
    gen_random_uuid(),
    0,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    'Tasting Room',
    gen_random_uuid()
);