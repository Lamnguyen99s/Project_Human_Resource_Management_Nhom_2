CREATE DATABASE if NOT EXISTS	hr_management;
USE hr_management;

-- department table
CREATE TABLE department(
	id INT PRIMARY KEY AUTO_INCREMENT,
	department_name VARCHAR(200),
	address VARCHAR(200)
			
);

INSERT INTO department(department_name, address)
VALUES
		('Technical Department', '10 floor'),
		('Marketing Department', '12 floor'),
		('Business Department', '11 floor'),
		('HR Department', '13 floor'),
		('Production Department', '15 floor'),
		('Accountant Department', '14 floor');
		

-- employee table
CREATE TABLE employee(
	id INT PRIMARY KEY AUTO_INCREMENT,
	employee_code VARCHAR(200) NOT NULL UNIQUE,
	full_name VARCHAR(200) NOT NULL,
	position ENUM('STAFF', 'FRESHER', 'INTERN'),
	gender ENUM('MALE', 'FEMALE'),
	age INT UNSIGNED,
	phone VARCHAR(200) UNIQUE,
	email VARCHAR(200) UNIQUE,
	salary DOUBLE,
	tax DOUBLE,
	hire_date DATETIME,
	end_date DATETIME,
	department_id INT,
	is_manager ENUM('1'),
	UNIQUE (department_id, is_manager),
	FOREIGN KEY (department_id) REFERENCES department(id)
);


INSERT INTO employee(employee_code, full_name, position, gender, age, phone, email, salary, tax, hire_date, end_date, department_id, is_manager)
VALUES
	('69798', 'Nguyễn Văn Nam', 'STAFF', 'MALE', 35, '0967217833', 'nam@gmail.com', 20000000, 650000, '2017-01-12 09:02:03', NULL, 1, '1'),
	('69634', 'Nguyễn Văn Minh', 'STAFF', 'MALE', 26, '0767217831', 'minh@gmail.com', 15000000, 200000, '2020-03-12 12:02:05', NULL, 4, NULL),
	('66778', 'Nguyễn Văn Hải', 'STAFF', 'MALE', 30, '0667217832', 'hai@gmail.com', 20000000, 650000, '2017-02-13 10:03:03', NULL, 4, '1'),
	('69777', 'Lê Văn Hải', 'STAFF', 'MALE', 25, '0667217843', 'hai1@gmail.com', 15000000, 200000, '2019-02-12 11:02:02', NULL, 2, NULL),
	('69378', 'Nguyễn Minh Ngọc', 'INTERN', 'FEMALE', 21, '0667217855', 'minh1ngoc@gmail.com', 9000000, 0, '2022-02-12 11:02:02', NULL, 1, NULL),
	('69799', 'Nguyễn Thị Lan', 'STAFF', 'FEMALE', 33, '0968456123', 'lannt@gmail.com', 20000000, 650000, '2017-01-12 09:05:03', NULL, 3, '1'),
	('69633', 'Trần Quốc Toàn ', 'FRESHER', 'MALE', 23, '0767217834', 'toantq@gmail.com', 12000000, 50000, '2021-03-12 12:02:05', NULL, 5, NULL),
	('66779', 'Nguyễn Quang Vũ', 'STAFF', 'MALE', 30, '0667217886', 'vunq@gmail.com', 20000000, 200000, '2018-02-13 10:03:03', NULL, 5, '1'),
	('69767', 'Lê Minh Tú', 'STAFF', 'FEMALE', 25, '0667217844', 'tulm@gmail.com', 15000000, 200000, '2020-02-12 11:02:02', NULL, 5, NULL),
	('69334', 'Hoàng Trọng Tuấn', 'INTERN', 'MALE', 21, '0667217866', 'tuanht@gmail.com', 9000000, 0, '2022-02-12 11:02:02', NULL, 2, NULL);
		

-- bảng admin
CREATE TABLE ADMIN(
	id INT PRIMARY KEY AUTO_INCREMENT,
	full_name VARCHAR(200),
	username VARCHAR(200),
	password VARCHAR(200)		
);

INSERT INTO admin(full_name, username, password)
VALUES
	('Dung Lam', 'Dunglamabc', 'abc@123'),
	('Admin', 'admin', 'admin');
		

-- SELECT * FROM employee 
-- WHERE full_name LIKE '%van%' OR employee_code = '69778' OR phone = '0987654321' OR email = 'hongmc@gmail.com'
-- 
-- SELECT * FROM employee AS emp
-- LEFT JOIN department AS dep
-- ON emp.department_id = dep.id


-- SELECT emp.*, dep.department_name FROM employee emp 
-- JOIN department dep 
-- ON emp.department_id = dep.id ORDER BY hire_date
-- 
-- UPDATE employee SET position = 'EMPLOYEE2', salary = 15000000, tax = 200000
-- WHERE employee_code = '69797' 

-- SELECT department_name,avg(salary) AS 'Highest average salary'   
-- FROM department 
-- JOIN employee
-- ON department.id = employee.department_id
-- GROUP BY department_name
-- ORDER BY avg(salary) DESC LIMIT 1;
-- 
-- 
-- SELECT emp.*, dep.department_name FROM employee emp 
-- LEFT JOIN department dep
-- ON emp.department_id = dep.id ORDER BY hire_date











