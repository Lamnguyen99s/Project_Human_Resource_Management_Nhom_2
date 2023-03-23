-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.32 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.3.0.6589
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for hr_management
CREATE DATABASE IF NOT EXISTS `hr_management` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `hr_management`;

-- Dumping structure for table hr_management.admin
CREATE TABLE IF NOT EXISTS `admin` (
  `id` int NOT NULL AUTO_INCREMENT,
  `full_name` varchar(200) DEFAULT NULL,
  `username` varchar(200) DEFAULT NULL,
  `password` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table hr_management.admin: ~2 rows (approximately)
INSERT INTO `admin` (`id`, `full_name`, `username`, `password`) VALUES
	(1, 'Dung Lam', 'Dunglamabc', 'abc@123'),
	(2, 'Admin', 'admin', 'admin');

-- Dumping structure for table hr_management.department
CREATE TABLE IF NOT EXISTS `department` (
  `id` int NOT NULL AUTO_INCREMENT,
  `department_name` varchar(200) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table hr_management.department: ~5 rows (approximately)
INSERT INTO `department` (`id`, `department_name`, `address`) VALUES
	(1, 'Technical Department', '10 floor'),
	(2, 'Marketing Department', '12 floor'),
	(3, 'Business Department', '11 floor'),
	(4, 'HR Department', '13 floor'),
	(5, 'Production Department', '15 floor');

-- Dumping structure for table hr_management.employee
CREATE TABLE IF NOT EXISTS `employee` (
  `id` int NOT NULL AUTO_INCREMENT,
  `employee_code` varchar(200) NOT NULL,
  `full_name` varchar(200) NOT NULL,
  `position` enum('MANAGER','EMPLOYEE1','EMPLOYEE2') DEFAULT NULL,
  `gender` enum('MALE','FEMALE') DEFAULT NULL,
  `age` int unsigned DEFAULT NULL,
  `phone` varchar(200) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  `salary` double DEFAULT NULL,
  `tax` double DEFAULT NULL,
  `hire_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `department_id` int DEFAULT NULL,
  `is_manager` enum('1') DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `employee_code` (`employee_code`),
  UNIQUE KEY `phone` (`phone`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `department_id` (`department_id`,`is_manager`),
  CONSTRAINT `employee_ibfk_1` FOREIGN KEY (`department_id`) REFERENCES `department` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table hr_management.employee: ~14 rows (approximately)
INSERT INTO `employee` (`id`, `employee_code`, `full_name`, `position`, `gender`, `age`, `phone`, `email`, `salary`, `tax`, `hire_date`, `end_date`, `department_id`, `is_manager`) VALUES
	(1, '69798', 'Nguyễn Văn Nam', 'MANAGER', 'MALE', 35, '0967217833', 'nam@gmail.com', 20000000, 650000, '2017-01-12 09:02:03', NULL, 1, '1'),
	(2, '69634', 'Nguyễn Văn Minh', 'EMPLOYEE1', 'MALE', 26, '0767217831', 'minh@gmail.com', 10000000, 0, '2020-03-12 12:02:05', NULL, 4, NULL),
	(3, '66778', 'Nguyễn Văn Hải', 'MANAGER', 'MALE', 30, '0667217832', 'hai@gmail.com', 20000000, 650000, '2017-02-13 10:03:03', NULL, 4, '1'),
	(4, '69777', 'Lê Văn Hải', 'EMPLOYEE2', 'MALE', 25, '0667217843', 'hai1@gmail.com', 15000000, 200000, '2019-02-12 11:02:02', NULL, 2, NULL),
	(5, '69378', 'Nguyễn Minh Ngọc', 'EMPLOYEE1', 'FEMALE', 29, '0667217855', 'minh1ngoc@gmail.com', 10000000, 0, '2022-02-12 11:02:02', NULL, 1, NULL),
	(6, '69799', 'Nguyễn Thị Lan', 'MANAGER', 'FEMALE', 33, '0968456123', 'lannt@gmail.com', 20000000, 650000, '2017-01-12 09:05:03', NULL, 3, '1'),
	(7, '69633', 'Trần Quốc Toàn ', 'EMPLOYEE2', 'MALE', 26, '0767217834', 'toantq@gmail.com', 15000000, 200000, '2021-03-12 12:02:05', NULL, 3, NULL),
	(8, '66779', 'Nguyễn Quang Vũ', 'MANAGER', 'MALE', 30, '0667217886', 'vunq@gmail.com', 20000000, 200000, '2018-02-13 10:03:03', NULL, 5, '1'),
	(9, '69767', 'Lê Minh Tú', 'EMPLOYEE1', 'FEMALE', 25, '0667217844', 'tulm@gmail.com', 10000000, 0, '2020-02-12 11:02:02', NULL, 5, NULL),
	(10, '69334', 'Hoàng Trọng Tuấn', 'EMPLOYEE1', 'MALE', 29, '0667217866', 'tuanht@gmail.com', 10000000, 0, '2022-02-12 11:02:02', NULL, 2, NULL),
	(11, '69877', 'Tú Uyên', 'EMPLOYEE2', 'FEMALE', 20, '0985197375', 'uyenntt3@gmail.com', 15000000, 200000, '2023-03-22 00:00:00', NULL, 2, NULL),
	(12, '69854', 'Nguyễn Trọng Lâm', 'MANAGER', 'MALE', 33, '0966888666', 'lamnt@gmail.com', 20000000, 650000, '2023-03-22 00:00:00', NULL, 2, '1'),
	(13, '67834', 'Nguyễn Đình Vũ', 'EMPLOYEE2', 'MALE', 25, '0335674666', 'vund3@gmail.com', 15000000, 200000, '2023-03-22 08:53:47', NULL, 1, NULL),
	(14, '69797', 'Đào Phương Uyên', 'EMPLOYEE1', 'FEMALE', 25, '0987123654', 'uyendp@gmail.com', 10000000, 0, '2023-03-22 10:23:58', NULL, 2, NULL);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
