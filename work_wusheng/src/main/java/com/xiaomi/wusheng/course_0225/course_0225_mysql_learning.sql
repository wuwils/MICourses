show databases;
select VERSION();
create database myexample;


create table users(
	id int auto_increment primary key,
	username varchar(50) not null,
	email varchar(100) not null,
	birthdate date,
	is_active boolean default true
);

insert into users (username, email, birthdate, is_active) values ('zhangsan', '12345@123.com', '2000-1-1', 1);


CREATE TABLE message_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    message_id VARCHAR(64) NOT NULL UNIQUE,
    content TEXT NOT NULL,
    receiver_id VARCHAR(64) NOT NULL,
    send_time DATETIME NOT NULL,
    status TINYINT NOT NULL DEFAULT 0,
    message_type VARCHAR(32) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


CREATE TABLE products (
    product_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    price float NOT NULL,
    inventory INT NOT NULL
);

CREATE TABLE sales (
    sale_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    sale_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
);


INSERT INTO products (product_name, price, inventory) VALUES
('Widget A', 20.00, 50),
('Widget B', 15.00, 30),
('Widget C', 30.00, 0);

UPDATE products 
SET price = 25.00, inventory = 40 
WHERE product_id = 1;

DELETE FROM products 
WHERE inventory = 0;

INSERT INTO sales (product_id, quantity) VALUES (2, 10);

UPDATE products 
SET inventory = inventory - 10 
WHERE product_id = 2;


CREATE TABLE books (
    book_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    publication_year INT NOT NULL,
    genre VARCHAR(100) NOT NULL,
    price FLOAT NOT NULL
);

INSERT INTO books (title, author, publication_year, genre, price) VALUES
('1111', 'AA', 1997, 'mm', 15.99),
('2222', 'AA', 1998, 'nn', 16.99),
('3333', 'BB', 2000, 'mm', 10.99),
('4444', 'BB', 2001, 'nn', 19.99),
('5555', 'CC', 2002, 'hh', 25.00);

SELECT * 
FROM books 
WHERE author = 'AA';

SELECT * 
FROM books 
WHERE publication_year > 2000;

SELECT * 
FROM books 
WHERE genre = 'mm' AND price < 20;

SELECT * 
FROM books 
WHERE price BETWEEN 10 AND 50 
ORDER BY price ASC;

SELECT * 
FROM books 
WHERE title LIKE '%33%';

use myexample;
create index id on books(book_id);
create index info on books(title, author);

CREATE TABLE orders (
    order_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    order_amount float NOT NULL
);

INSERT INTO orders (customer_id, order_date, order_amount) VALUES
(101, '2023-01-15', 150.00),
(102, '2023-02-10', 200.00),
(101, '2023-03-05', 100.00),
(103, '2023-04-20', 250.00),
(101, '2023-05-18', 300.00);

SELECT 
    customer_id,
    COUNT(order_id) AS total_orders,
    SUM(order_amount) AS total_order_amount,
    AVG(order_amount) AS average_order_amount,
    MAX(order_amount) AS max_order_amount,
    MIN(order_amount) AS min_order_amount
FROM 
    orders
GROUP BY 
    customer_id;


CREATE TABLE users_name (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    middle_name VARCHAR(255)
);

INSERT INTO users_name (first_name, last_name, middle_name) VALUES
('John', 'Doe', 'Michael'),
('Jane', 'Smith', NULL),
('Alice', 'Johnson', 'Beth');

SELECT 
    user_id,
    first_name,
    last_name,
    middle_name,
    CASE 
        WHEN middle_name IS NULL THEN CONCAT(first_name, ' ', last_name)
        ELSE CONCAT(first_name, ' ', middle_name, ' ', last_name)
    END AS full_name
FROM 
    users_name;


CREATE TABLE employees (
    employee_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_name VARCHAR(255) NOT NULL
);

CREATE TABLE projects (
    project_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id BIGINT,
    project_name VARCHAR(255) NOT NULL
);

INSERT INTO employees (employee_name) VALUES
('Alice Johnson'),
('Bob Smith'),
('Charlie Brown');

INSERT INTO projects (employee_id, project_name) VALUES
(1, 'Project Alpha'),
(1, 'Project Beta'),
(2, 'Project Gamma');

SELECT 
    e.employee_name,
    COALESCE(p.project_name, '未分配项目') AS projects
FROM 
    employees e
LEFT JOIN 
    projects p ON e.employee_id = p.employee_id
ORDER BY 
    e.employee_name;




