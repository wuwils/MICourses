CREATE USER IF NOT EXISTS test@'%' IDENTIFIED BY 'password';

DROP DATABASE IF EXISTS test;
CREATE DATABASE test;

GRANT ALL PRIVILEGES oN test.* To test@'%' WITH GRANT OPTION;
FLUSH PRIVILEGES;

USE test;
CREATE TABLE students(
    id BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(50) NOT NULL,
    age INT NOT NULL,
    grade varchar(10) NOT NULL,
    PRIMARY KEY(id)
)Engine=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO students(name, age, grade) VALUES ('小赵', 20, 'A');
INSERT INTO students(name, age, grade) VALUES ('小钱', 20, 'B');
INSERT INTO students(name, age, grade) VALUES ('小孙', 20, 'C');
INSERT INTO students(name, age, grade) VALUES ('小李', 21, 'A');
INSERT INTO students(name, age, grade) VALUES ('小周', 22, 'A');

USE test;
CREATE TABLE accounts (
    account_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(255) NOT NULL,
    balance DECIMAL(10, 2) NOT NULL
);
INSERT INTO accounts (user_name, balance) VALUES ('UserA', 1000.00), ('UserB', 500.00);


use test;

CREATE TABLE employees (
    employee_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    department VARCHAR(50) NOT NULL
);


INSERT INTO employees (first_name, last_name, department) VALUES
('John', 'Smith', 'HR'),
('Jane', 'Doe', 'IT'),
('Alice', 'Smith', 'Finance'),
('Bob', 'Brown', 'Marketing'),
('Charlie', 'Smith', 'Sales');

EXPLAIN SELECT * FROM employees WHERE last_name = 'Smith';

CREATE INDEX idx_last_name ON employees(last_name);

EXPLAIN SELECT * FROM employees WHERE last_name = 'Smith';

