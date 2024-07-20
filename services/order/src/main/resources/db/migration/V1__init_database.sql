-- Table creation script

CREATE TABLE IF NOT EXISTS t_order
(
    id              INTEGER         NOT NULL PRIMARY KEY,
    reference       VARCHAR(255),
    total_amount    NUMERIC(10, 2),
    payment_method  VARCHAR(50),
    customer_id     VARCHAR(255),
    created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP
);

CREATE TABLE IF NOT EXISTS t_order_line
(
    id          INTEGER NOT NULL PRIMARY KEY,
    order_id    INTEGER,
    product_id  INTEGER,
    quantity    INTEGER,
    FOREIGN KEY (order_id) REFERENCES t_order (id) ON DELETE CASCADE
);

-- Create a sequence for the primary key of each table

CREATE SEQUENCE IF NOT EXISTS order_id_seq INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS order_line_id_seq INCREMENT BY 1;

-- Create enum type for payment_method
DO $$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'payment_method') THEN
            CREATE TYPE payment_method AS ENUM (
                'VISA',
                'MASTERCARD',
                'AMERICAN_EXPRESS',
                'PAYPAL',
                'GOOGLE_PAY',
                'APPLE_PAY',
                'BITCOIN'
                );
        END IF;
    END$$;

-- Alter t_order table to use the enum type
ALTER TABLE t_order
    ALTER COLUMN payment_method TYPE payment_method USING payment_method::payment_method;

-- Create index on order_id in t_order_line for better performance
CREATE INDEX IF NOT EXISTS idx_order_line_order_id ON t_order_line(order_id);