-- table creation script

CREATE TABLE IF NOT EXISTS category
(
    id          INTEGER      NOT NULL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description TEXT
);

CREATE TABLE IF NOT EXISTS product
(
    id                INTEGER      NOT NULL PRIMARY KEY,
    name              VARCHAR(255) NOT NULL,
    description       TEXT,
    quantity_in_stock INTEGER,
    unit_price        NUMERIC(10, 2),
    category_id       INTEGER,
    FOREIGN KEY (category_id) REFERENCES category (id) ON DELETE CASCADE
);
