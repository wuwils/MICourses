/*
此文件包含了 BookBorrowReturn.sql 的全部内容
测试结果保存在 src/main/resources/work_0225/question_2/
*/



-- 书籍表：
CREATE TABLE books (
	book_id INT PRIMARY KEY AUTO_INCREMENT,
	title VARCHAR(100) NOT NULL,
	stock INT DEFAULT 0 COMMENT '库存数量'
);

-- 借阅记录表
CREATE TABLE borrow_records(
	record_id INT PRIMARY KEY AUTO_INCREMENT,
	book_id INT NOT NULL,
	user_id INT NOT NULL,
	borrow_date DATE,
	FOREIGN KEY (book_id) REFERENCES books (book_id)
);

-- 向books表插入测试数据
INSERT INTO books (title, stock) VALUES
('AAAAA', 0),
('BBBBB', 1),
('CCCCC', 3),
('DDDDD', 4),
('EEEEE', 5),
('FFFFF', 6);


-- 向borrow_records表插入测试数据
INSERT INTO borrow_records (book_id, user_id, borrow_date) VALUES
(1, 101, '2025-02-01'),
(2, 102, '2025-02-02'),
(3, 103, '2025-02-03'),
(3, 104, '2025-02-04'),
(5, 105, '2025-02-05'),
(6, 106, '2025-02-06');




