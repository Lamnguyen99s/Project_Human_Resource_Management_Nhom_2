package com.ntl;

import java.sql.*;

public class JdbcEmployee {
    // get data employee
    public ResultSet getDataEmployee() throws ClassNotFoundException, SQLException {
        Connection connection = null;

        // create Driver connect to MySQL
        Class.forName("com.mysql.cj.jdbc.Driver");

        // connect to database 'hr_management'
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hr_management", "root", "root");

        // create statement
        Statement statement = connection.createStatement();

        // get data from table 'employee'
        ResultSet resultSet = statement.executeQuery("SELECT * FROM employee");
        return resultSet;
    }

    // insert data employee
    public Integer insertDataEmployee(String employeeCode, String fullName, String position, String gender, int age, String phone,
                                      String email, double salary, double tax, String hireDate, int departmentId, String isManager)
            throws ClassNotFoundException, SQLException {
        Connection connection = null;

        // create Driver connect to MySQL
        Class.forName("com.mysql.cj.jdbc.Driver");

        // connect to database 'hr_management'
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hr_management", "root", "root");

        // create statement
        Statement statement = connection.createStatement();

        // insert data into database
        System.out.println("Inserting records into the table...");

        String sql = "INSERT INTO employee (employee_code, full_name, position, gender, age, phone, email, salary, tax, hire_date, " +
                "department_id, is_manager) " +
                "VALUES ('" + employeeCode + "', '" + fullName + "', '" + position + "', '" + gender + "', '" + age + "', '" + phone + "'," +
                "'" + email + "', '" + salary + "', '" + tax + "', '" + hireDate + "', '" + departmentId + "', " + isManager + ")";

        Integer res = statement.executeUpdate(sql);
        System.out.println("Insert data successfully");
        return res;
    }

    // update data employee
    public Integer updateDataEmployee(String employeeCodeOld, String employeeCodeNew, String fullName, String position,
                                      String gender, int age, String phone, String email, double salary, double tax, int departmentId,
                                      String isManager) throws ClassNotFoundException, SQLException {
        Connection connection = null;

        // create Driver connect to MySQL
        Class.forName("com.mysql.cj.jdbc.Driver");

        // connect to database 'hr_management'
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hr_management", "root", "root");

        // create statement
        Statement statement = connection.createStatement();

        // update data in database
        System.out.println("Update records into the table...");


        String sql = "UPDATE employee SET employee_code = '" + employeeCodeNew + "', full_name = '" + fullName + "'," +
                "position = '" + position + "', gender = '" + gender + "', age = '" + age + "', phone = '" + phone + "', email = '" + email + "'," +
                "salary = '" + salary + "', tax = '" + tax + "', department_id = '" + departmentId + "', " +
                "is_manager = " + isManager + " WHERE employee_code = '" + employeeCodeOld + "'";
        Integer res = statement.executeUpdate(sql);
        System.out.println("Update records successfully!");
        return res;
    }

    // delete data employee
    public Integer deleteDataEmployee(String employeeCode) throws ClassNotFoundException, SQLException {
        Connection connection = null;

        // create Driver connect to MySQL
        Class.forName("com.mysql.cj.jdbc.Driver");

        // connect to database 'hr_management'
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hr_management", "root", "root");

        // create statement
        Statement statement = connection.createStatement();

        // delete data in database
        System.out.println("Delete records into the table...");

        String sql = "DELETE FROM employee WHERE employee_code = '" + employeeCode + "'";
        Integer res = statement.executeUpdate(sql);
        System.out.println("Delete records successfully!");
        return res;
    }

    // search data employee
    public ResultSet searchDataEmployee(String name) throws ClassNotFoundException, SQLException {
        Connection connection = null;

        // create Driver connect to MySQL
        Class.forName("com.mysql.cj.jdbc.Driver");

        // connect to database 'hr_management'
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hr_management", "root", "root");

        // create statement
        Statement statement = connection.createStatement();

        // search data from table 'employee'
        ResultSet resultSet = statement.executeQuery("SELECT * FROM employee WHERE full_name LIKE '%" + name + "%'");
        return resultSet;
    }
}
