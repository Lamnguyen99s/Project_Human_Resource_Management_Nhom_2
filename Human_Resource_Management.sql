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
	position ENUM('MANAGER', 'EMPLOYEE1', 'EMPLOYEE2'),
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


INSERT INTO employee(employee_code, full_name, position, gender, age, phone, email, salary, tax, hire_date, end_date)
VALUES
		('69798', 'Nguyễn Văn Nam', 'MANAGER', 'MALE', 35, '0967217833', 'nam@gmail.com', 20000000, 650000, '2017-01-12 09:02:03', NULL),  
		('69634', 'Nguyễn Văn Minh', 'EMPLOYEE1', 'MALE', 26, '0767217831', 'minh@gmail.com',10000000, 0, '2020-03-12 12:02:05', NULL),
		('66778', 'Nguyễn Văn Hải', 'MANAGER', 'MALE', 30, '0667217832', 'hai@gmail.com', 20000000, 650000, '2017-02-13 10:03:03', NULL),
		('69777', 'Lê Văn Hải', 'EMPLOYEE2', 'MALE', 25, '0667217843', 'hai1@gmail.com',15000000, 200000, '2019-02-12 11:02:02', NULL),
		('69378', 'Nguyễn Minh Ngọc', 'EMPLOYEE1', 'FEMALE', 29, '0667217855', 'minh1ngoc@gmail.com',10000000, 0, '2022-02-12 11:02:02', NULL),
		('69799', 'Nguyễn Thị Lan', 'MANAGER', 'FEMALE', 33, '0968456123', 'lannt@gmail.com', 20000000, 650000, '2017-01-12 09:05:03', NULL),  
		('69633', 'Trần Quốc Toàn ', 'EMPLOYEE2', 'MALE', 26, '0767217834', 'toantq@gmail.com',15000000, 200000, '2021-03-12 12:02:05', NULL),
		('66779', 'Nguyễn Quang Vũ', 'MANAGER', 'MALE', 30, '0667217886', 'vunq@gmail.com', 20000000, 200000, '2018-02-13 10:03:03', NULL),
		('69767', 'Lê Minh Tú', 'EMPLOYEE1', 'FEMALE', 25, '0667217844', 'tulm@gmail.com',10000000, 0, '2020-02-12 11:02:02', NULL),
		('69334', 'Hoàng Trọng Tuấn', 'EMPLOYEE1', 'MALE', 29, '0667217866', 'tuanht@gmail.com',10000000, 0, '2022-02-12 11:02:02', NULL);
		

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





















