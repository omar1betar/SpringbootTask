INSERT INTO orders (product_name, order_id, description, amount, status, created_at)
VALUES
    ('Smartphone', UNHEX(REPLACE(UUID(), '-', '')), 'Electronics order for a smartphone', 599.99, 'NEW', CURRENT_TIMESTAMP),
    ('Washing Machine', UNHEX(REPLACE(UUID(), '-', '')), 'Home appliance order for a washing machine', 799.99, 'NEW', CURRENT_TIMESTAMP),
    ('Novel', UNHEX(REPLACE(UUID(), '-', '')), 'Book order for a novel', 19.99, 'NEW', CURRENT_TIMESTAMP),
    ('Vegetables', UNHEX(REPLACE(UUID(), '-', '')), 'Grocery order for fresh vegetables', 25.49, 'NEW', CURRENT_TIMESTAMP),
    ('Jacket', UNHEX(REPLACE(UUID(), '-', '')), 'Clothing order for a jacket', 89.99, 'NEW', CURRENT_TIMESTAMP),
    ('Running Shoes', UNHEX(REPLACE(UUID(), '-', '')), 'Footwear order for running shoes', 120.00, 'NEW', CURRENT_TIMESTAMP),
    ('Gaming Console', UNHEX(REPLACE(UUID(), '-', '')), 'Gaming console order', 299.99, 'NEW', CURRENT_TIMESTAMP),
    ('Dining Table', UNHEX(REPLACE(UUID(), '-', '')), 'Furniture order for a dining table', 499.99, 'NEW', CURRENT_TIMESTAMP),
    ('Dog Food', UNHEX(REPLACE(UUID(), '-', '')), 'Pet supplies order for dog food', 45.00, 'NEW', CURRENT_TIMESTAMP),
    ('Yoga Mat', UNHEX(REPLACE(UUID(), '-', '')), 'Fitness equipment order for a yoga mat', 20.00, 'NEW', CURRENT_TIMESTAMP);
