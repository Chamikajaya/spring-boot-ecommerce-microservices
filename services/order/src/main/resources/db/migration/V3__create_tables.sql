
-- Create t_order table
CREATE TABLE IF NOT EXISTS t_order
(
    id              INTEGER         NOT NULL DEFAULT nextval('order_id_seq') PRIMARY KEY,
    reference       VARCHAR(255)    NOT NULL UNIQUE,
    total_amount    NUMERIC(10, 2)  NOT NULL CHECK (total_amount >= 0),
    payment_method  payment_method  NOT NULL,
    customer_id     VARCHAR(255)    NOT NULL,
    created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create t_order_line table
CREATE TABLE IF NOT EXISTS t_order_line
(
    id          INTEGER NOT NULL DEFAULT nextval('order_line_id_seq') PRIMARY KEY,
    order_id    INTEGER NOT NULL,
    product_id  INTEGER NOT NULL,
    quantity    INTEGER NOT NULL CHECK (quantity > 0),
    unit_price  NUMERIC(10, 2) NOT NULL CHECK (unit_price >= 0),
    FOREIGN KEY (order_id) REFERENCES t_order (id) ON DELETE CASCADE
);
