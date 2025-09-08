CREATE TABLE students (
    student_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    date_of_birth DATE,
    class_id INT
);

CREATE TABLE classes (
    class_id INT AUTO_INCREMENT PRIMARY KEY,
    class_name VARCHAR(50) NOT NULL,
    teacher_id INT
);

CREATE TABLE enrollments (
    enrollment_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    class_id INT,
    enrollment_date DATE
);


INSERT INTO classes (class_name, teacher_id) VALUES ('Math', 1), ('Science', 2);
INSERT INTO students (first_name, last_name, date_of_birth, class_id) VALUES 
('John', 'Smith', '2000-01-01', 1),
('Jane', 'Doe', '2001-02-02', 1),
('Alice', 'Smith', '2002-03-03', 2),
('Bob', 'Brown', '2003-04-04', 2),
('Charlie', 'Smith', '2004-05-05', 1);

INSERT INTO enrollments (student_id, class_id, enrollment_date) VALUES 
(1, 1, '2023-01-01'),
(2, 1, '2023-02-01'),
(3, 2, '2023-03-01'),
(4, 2, '2023-04-01'),
(5, 1, '2023-05-01');

SELECT * FROM students WHERE last_name = 'Smith';

EXPLAIN SELECT * FROM students WHERE last_name = 'Smith';

CREATE INDEX idx_last_name ON students(last_name);

EXPLAIN SELECT * FROM students WHERE last_name = 'Smith';

SELECT * FROM enrollments WHERE student_id = 1;

EXPLAIN SELECT * FROM enrollments WHERE student_id = 1;

CREATE INDEX idx_student_id ON enrollments(student_id);

EXPLAIN SELECT * FROM enrollments WHERE student_id = 1;

SELECT * FROM students WHERE class_id = 1 ORDER BY last_name;

EXPLAIN SELECT * FROM students WHERE class_id = 1 ORDER BY last_name;

CREATE INDEX idx_class_id_last_name ON students(class_id, last_name);

EXPLAIN SELECT * FROM students WHERE class_id = 1 ORDER BY last_name;

SELECT * FROM enrollments WHERE enrollment_date > '2023-01-01';

EXPLAIN SELECT * FROM enrollments WHERE enrollment_date > '2023-01-01';

CREATE INDEX idx_enrollment_date ON enrollments(enrollment_date);

EXPLAIN SELECT * FROM enrollments WHERE enrollment_date > '2023-01-01';

SELECT s.student_id, s.first_name, s.last_name, c.class_name 
FROM students s 
JOIN classes c ON s.class_id = c.class_id 
WHERE s.class_id = 1;

EXPLAIN SELECT s.student_id, s.first_name, s.last_name, c.class_name 
FROM students s 
JOIN classes c ON s.class_id = c.class_id 
WHERE s.class_id = 1;

CREATE INDEX idx_students_class_id ON students(class_id);
CREATE INDEX idx_classes_class_id ON classes(class_id);

EXPLAIN SELECT s.student_id, s.first_name, s.last_name, c.class_name 
FROM students s 
JOIN classes c ON s.class_id = c.class_id 
WHERE s.class_id = 1;

