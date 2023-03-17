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
    public Integer insertDataEmployee(String employeeCode, String fullName, String position, Integer age, String phone,
                                      String email, Integer salary, Integer tax, Date hireDate, Integer departmentId,
                                      Enum isManager) throws ClassNotFoundException, SQLException {
        Connection connection = null;

        // create Driver connect to MySQL
        Class.forName("com.mysql.cj.jdbc.Driver");

        // connect to database 'hr_management'
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hr_management", "root", "root");

        // create statement
        Statement statement = connection.createStatement();

        // insert data into database
        System.out.println("Inserting records into the table...");

        String sql = "INSERT INTO employee (employee_code, full_name, position, age, phone, email, salary, tax, hire_date, " +
                "department_id, is_manager) " +
                "VALUES ('" + employeeCode + "', '" + fullName + "', '" + position + "', '" + age + "', '" + phone + "'," +
                "'" + email + "', '" + salary + "', '" + tax + "', '" + hireDate + "', '" + departmentId + "', '" + isManager + "')";

        Integer res = statement.executeUpdate(sql);
        System.out.println("Insert data successfully");
        return res;
    }
}
