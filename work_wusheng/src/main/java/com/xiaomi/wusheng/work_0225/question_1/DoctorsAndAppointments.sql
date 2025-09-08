/*
此文件包含了 DoctorsAndAppointments.sql 的全部内容
测试结果保存在 src/main/resources/work_0225/question_1/
*/



-- 医生表（前置结构，需优化）
CREATE TABLE doctors(
	doctor_id INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
	department VARCHAR(50) COMMENT '科室',
	titLe VARCHAR(50) COMMENT '职称'
);


-- 患者预约表（已有完整结构）
CREATE TABLE appointments (
	appointment_id INT PRIMARY KEY AUTO_INCREMENT,
	doctor_id INT NOT NULL,
	patient_name VARCHAR(50),
	appointment_time DATETIME COMMENT '预约时间(格式: YYYY-MM-DD HH:MM:SS)',
	status ENUM('Pending', 'Completed') DEFAULT 'Pending',
	FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id)
);


-- 一、表结构优化
-- 1.添加hire_date入职日期字段 (DATE类型)
ALTER TABLE doctors ADD COLUMN hire_date DATE;

-- 2.新增work_shift接诊时段字段(ENUM类型，取值AM/PM)
ALTER TABLE doctors ADD COLUMN work_shift ENUM('AM','PM');

-- 3.修改title职称字段类型为VARCHAR(100)
ALTER TABLE doctors MODIFY COLUMN title VARCHAR(100);


-- 插入医生数据
INSERT INTO doctors (name, department, title, hire_date, work_shift) VALUES
('张三', 'Pediatrics', NULL, '2010-08-21', NULL),
('李四', 'Pediatrics', '主任医师', '2012-08-21', NULL),
('王五', 'Department2', NULL, '2015-08-21', NULL),
('老六', 'Department2', '副主任医师', '2017-08-21', NULL),
('老七', 'Department3', NULL, '2019-08-21', NULL),
('老八', 'Department3', '主任医师', '2020-08-21', NULL);


-- 插入预约数据
INSERT INTO appointments (doctor_id, patient_name, appointment_time, status) VALUES
(1, 'Patient1', '2023-08-21 08:00:00', 'Completed'),
(2, 'Patient2', '2023-08-21 09:00:00', 'Completed'),
(1, 'Patient3', '2023-08-22 10:00:00', 'Pending'),
(3, 'Patient4', '2023-08-21 11:00:00', 'Completed'),
(3, 'Patient5', '2023-08-21 12:00:00', 'Pending'),
(4, 'Patient6', '2023-08-21 13:00:00', 'Completed'),
(5, 'Patient7', '2023-08-21 14:00:00', 'Completed'),
(5, 'Patient8', '2023-08-22 15:00:00', 'Pending'),
(6, 'Patient9', '2023-08-21 16:30:00', 'Completed'),
(6, 'Patient10', '2023-08-21 17:00:00', 'Pending');


-- 二、数据操作
-- 1.将2020年前入职的医生work_shift设置为'AM'
UPDATE doctors SET work_shift = 'AM' WHERE hire_date < '2020-01-01';

-- 2.把所有Pediatrics科室医生的职称更新为"副主任医师'(若原职称为空)
UPDATE doctors SET title = '副主任医师' WHERE department = 'Pediatrics' AND title IS NULL;

-- 三、数据分析
-- 1.统计各科室医生的平均接诊量(显示科室、医生数、平均接诊量)
SELECT
    d.department AS 科室,
    COUNT(DISTINCT d.doctor_id) AS 医生数,
    AVG(a.appointments_count) AS 平均接诊量
FROM doctors d
LEFT JOIN (
    SELECT doctor_id, COUNT(*) AS appointments_count
    FROM appointments
    GROUP BY doctor_id
) a ON d.doctor_id = a.doctor_id
GROUP BY d.department;

-- 2.查询2023年8月21日各时段(AM/PM)的完成率(Completed数/总预约数)
SELECT
    CASE
        WHEN HOUR(appointment_time) < 12 THEN 'AM'
        ELSE 'PM'
    END AS 时段,
    CONCAT(ROUND(SUM(CASE WHEN status = 'Completed' THEN 1 ELSE 0 END) / COUNT(*) * 100, 2), '%') AS 完成率
FROM appointments
WHERE DATE(appointment_time) = '2023-08-21'
GROUP BY 时段;

-- 3.查询入职5年以上(截至当前日期)的医生信息，格式："李建国(主任医师) | 2015年入职"
SELECT
    CONCAT(name, IF(title IS NOT NULL, CONCAT('(', title, ')'), '') , ' | ', YEAR(hire_date), '年入职') AS 医生信息
FROM doctors
WHERE hire_date <= DATE_SUB(CURDATE(), INTERVAL 5 YEAR);


