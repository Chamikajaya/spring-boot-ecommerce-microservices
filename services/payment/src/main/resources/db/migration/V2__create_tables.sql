-- Create t_payment table
CREATE TABLE IF NOT EXISTS t_payment
(
    id             INTEGER        NOT NULL DEFAULT nextval('payment_id_seq') PRIMARY KEY,
    reference      VARCHAR(255)   NOT NULL UNIQUE,
    payment_method VARCHAR(255)   NOT NULL,
    amount         NUMERIC(10, 2) NOT NULL CHECK (amount >= 0),
    payment_status BOOLEAN,
    order_id       INTEGER        NOT NULL,
    created_at     TIMESTAMP      NOT NULL,
    updated_at     TIMESTAMP
);

