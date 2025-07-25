-- Order Management System Database Setup
-- Based on the schema from prompt.txt

-- Drop and create the database
DROP DATABASE IF EXISTS oms_db;
GO

CREATE DATABASE oms_db;
GO

USE oms_db;
GO

-- Create orders table
CREATE TABLE orders (
    id INT PRIMARY KEY IDENTITY,
    user_id INT NOT NULL,
    order_date DATETIME,
    order_status VARCHAR(50),          -- "Pending", "Shipped", "Delivered"
    order_total DECIMAL(10, 2)
);

-- Create order_items table
CREATE TABLE order_items (
    id INT PRIMARY KEY IDENTITY,
    order_id INT,
    product_id INT NOT NULL,
    sku VARCHAR(50),
    quantity INT,
    unit_price DECIMAL(10,2),
    discount DECIMAL(10,2),
    final_price DECIMAL(10,2),
    size VARCHAR(20),
    color VARCHAR(30),
    status VARCHAR(50),  -- e.g., Ordered, Cancelled, Returned
    seller_id INT NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id)
);

-- Create order_invoice table
CREATE TABLE order_invoice (
    id INT PRIMARY KEY IDENTITY,
    order_id INT,
    invoice_number VARCHAR(100),
    invoice_date DATETIME,
    payment_mode VARCHAR(50),         -- "Credit Card", "UPI", "COD"
    invoice_amount DECIMAL(10, 2),
    FOREIGN KEY (order_id) REFERENCES orders(id)
);

-- Create shipments table
CREATE TABLE shipments (
    id INT PRIMARY KEY IDENTITY,
    order_id INT,
    shipment_status VARCHAR(50),       -- e.g., "In Transit", "Delivered"
    shipment_tracking_id VARCHAR(100),
    shipment_date DATETIME,
    delivered_date DATETIME,
    FOREIGN KEY (order_id) REFERENCES orders(id)
);

-- Create shipment_items table
CREATE TABLE shipment_items (
    id INT PRIMARY KEY IDENTITY,
    shipment_id INT,
    order_item_id INT,
    item_status VARCHAR(50),           -- e.g., "In Transit", "Delivered"
    FOREIGN KEY (shipment_id) REFERENCES shipments(id),
    FOREIGN KEY (order_item_id) REFERENCES order_items(id)
);

-- Create order_returns table
CREATE TABLE order_returns (
    id INT PRIMARY KEY IDENTITY,
    order_item_id INT,
    return_date DATETIME,
    return_reason VARCHAR(255),
    return_status VARCHAR(50), -- e.g., Requested, Approved, Rejected, Completed
    refund_amount DECIMAL(10,2),
    FOREIGN KEY (order_item_id) REFERENCES order_items(id)
);

-- Insert sample data for testing
INSERT INTO orders (user_id, order_date, order_status, order_total) VALUES
(1, GETDATE(), 'Pending', 299.99),
(2, GETDATE(), 'Shipped', 149.50);

INSERT INTO order_items (order_id, product_id, sku, quantity, unit_price, discount, final_price, size, color, status, seller_id) VALUES
(1, 101, 'SKU001', 2, 150.00, 0.01, 149.99, 'M', 'Blue', 'Ordered', 1),
(1, 102, 'SKU002', 1, 150.00, 0.00, 150.00, 'L', 'Red', 'Ordered', 1),
(2, 103, 'SKU003', 1, 149.50, 0.00, 149.50, 'S', 'Green', 'Shipped', 2);

PRINT 'Database oms_db created successfully with sample data!';
