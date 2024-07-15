-- Insert categories


INSERT INTO category (id, name, description)
VALUES (nextval('category_id_seq'), 'Men', 'Men''s clothing'),
       (nextval('category_id_seq'), 'Women', 'Women''s clothing'),
       (nextval('category_id_seq'), 'Kids', 'Kids'' clothing'),
       (nextval('category_id_seq'), 'Accessories', 'Clothing accessories'),
       (nextval('category_id_seq'), 'Footwear', 'Various types of footwear');

-- Insert products

-- Insert products for Men category (id: 1)
INSERT INTO product (id, name, description, quantity_in_stock, unit_price, category_id)
VALUES (nextval('product_id_seq'), 'Men''s T-Shirt', 'Comfortable cotton T-shirt', 100, 19.99, 1),
       (nextval('product_id_seq'), 'Men''s Jeans', 'Regular fit blue jeans', 50, 49.99, 1),
       (nextval('product_id_seq'), 'Men''s Jacket', 'Stylish leather jacket', 30, 89.99, 1);

-- Insert products for Women category (id: 51)
INSERT INTO product (id, name, description, quantity_in_stock, unit_price, category_id)
VALUES (nextval('product_id_seq'), 'Women''s Dress', 'Elegant evening dress', 40, 59.99, 2),
       (nextval('product_id_seq'), 'Women''s Blouse', 'Casual wear blouse', 60, 29.99, 2),
       (nextval('product_id_seq'), 'Women''s Skirt', 'Fashionable mini skirt', 20, 39.99, 2);

-- Insert products for Kids category (id: 101)
INSERT INTO product (id, name, description, quantity_in_stock, unit_price, category_id)
VALUES (nextval('product_id_seq'), 'Kids'' T-Shirt', 'Colorful cartoon T-shirt', 80, 14.99, 3),
       (nextval('product_id_seq'), 'Kids'' Shorts', 'Comfortable cotton shorts', 70, 19.99, 3),
       (nextval('product_id_seq'), 'Kids'' Sweater', 'Warm woolen sweater', 25, 24.99, 3);

-- Insert products for Accessories category (id: 151)
INSERT INTO product (id, name, description, quantity_in_stock, unit_price, category_id)
VALUES (nextval('product_id_seq'), 'Leather Belt', 'Genuine leather belt', 100, 25.99, 4),
       (nextval('product_id_seq'), 'Sunglasses', 'Polarized sunglasses', 50, 45.99, 4),
       (nextval('product_id_seq'), 'Hat', 'Stylish summer hat', 60, 19.99, 4);

-- Insert products for Footwear category (id: 201)
INSERT INTO product (id, name, description, quantity_in_stock, unit_price, category_id)
VALUES (nextval('product_id_seq'), 'Running Shoes', 'Lightweight running shoes', 70, 59.99, 5),
       (nextval('product_id_seq'), 'Boots', 'Durable hiking boots', 40, 79.99, 5),
       (nextval('product_id_seq'), 'Sandals', 'Comfortable summer sandals', 90, 29.99, 5);