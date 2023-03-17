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
	position VARCHAR(200),
	age INT UNSIGNED,
	phone VARCHAR(200) UNIQUE,
	email VARCHAR(200) UNIQUE,
	salary DOUBLE,
	tax DOUBLE,
	hire_date DATE,
	end_date DATE,
	department_id INT,
	is_manager ENUM('1'),
	UNIQUE (department_id, is_manager),
	FOREIGN KEY (department_id) REFERENCES department(id)
);


INSERT INTO employee(employee_code, full_name, position, age, phone, email, salary, hire_date, end_date)
VALUES
		('69798', 'Nguyễn Văn Nam', 'Technical manager', 35, '096721783', 'nam@gmail.com', 50000000, '2019-02-12', NULL),  
		('69634', 'Nguyễn Văn Minh', 'Technical employee', 26, '076721783', 'minh@gmail.com',20000000, '2020-03-12', NULL),
		('66778', 'Nguyễn Văn Hải', 'HR Manager', 30, '066721783', 'hai@gmail.com', 50000000, '2022-02-12', NULL),
		('69777', 'Lê Văn Hải', 'HR employee', 25, '066721784', 'hai1@gmail.com',10000000, '2022-02-12', NULL),
		('69378', 'Minh Ngọc', 'Production manager', 29, '066721785', 'minh1ngoc@gmail.com',10000000, '2022-02-12', NULL),
		('69768', 'Ngọc Ánh', 'Marketing employee', 23, '066721786', 'hai2@gmail.com', 20000000, '2022-02-12', NULL),
		('69788','Nguyễn Sao Mai', 'Production employee', 21, '096721784', 'mai1@gmail.com',10000000, '2023-02-12', NULL),
		('69758', 'Nguyễn Lấp Lánh', 'Accountant employee', 22, '066721787', 'lap1@gmail.com',10000000, '2021-02-12', NULL),
		('69375', 'Nguyễn Ánh', 'Business employee', 24, '066721788', 'hai3@gmail.com',10000000, '2022-02-12', NULL),
		('69778', 'Mạc Hồng', 'Marketing Manager', 36, '066721789', 'hai4@gmail.com', 60000000, '2022-02-12', NULL);


-- bảng admin
CREATE TABLE ADMIN(
	id INT PRIMARY KEY AUTO_INCREMENT,
	full_name VARCHAR(200),
	username VARCHAR(200),
	password VARCHAR(200)		
);

INSERT INTO admin(full_name, username, password)
VALUES
	('Dung Lam', 'Dunglamabc', 'abc@123');
		


		



















