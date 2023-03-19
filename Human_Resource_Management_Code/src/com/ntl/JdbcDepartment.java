package com.ntl;

import java.sql.*;

public class JdbcDepartment {
    // get data department
    public ResultSet getDataDepartment() throws ClassNotFoundException, SQLException {
        Connection connection = null;

        // create Driver connect to MySQL
        Class.forName("com.mysql.cj.jdbc.Driver");

        // connect to database 'hr_management'
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hr_management", "root", "root");

        // create statement
        Statement statement = connection.createStatement();

        // get data from table 'department'
        ResultSet resultSet = statement.executeQuery("SELECT * FROM department");
        return resultSet;
    }

    // insert data department
    public Integer insertDataDepartment(String departmentName, String address)
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

        String sql = "INSERT INTO department (department_name, address) " +
                "VALUES ('" + departmentName + "', '" + address + "')";

        Integer res = statement.executeUpdate(sql);
        System.out.println("Insert data successfully");
        return res;
    }

    // update data department
    public Integer updateDataDepartment(int id, String departmentName, String address)
            throws ClassNotFoundException, SQLException {
        Connection connection = null;

        // create Driver connect to MySQL
        Class.forName("com.mysql.cj.jdbc.Driver");

        // connect to database 'hr_management'
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hr_management", "root", "root");

        // create statement
        Statement statement = connection.createStatement();

        // update data in database
        System.out.println("Update records into the table...");


        String sql = "UPDATE department SET department_name = '" + departmentName + "', " +
                "address = '" + address + "' WHERE id = '" + id + "'";
        Integer res = statement.executeUpdate(sql);
        System.out.println("Update records successfully!");
        return res;
    }

    // delete data department
    public Integer deleteDataDepartment(int id) throws ClassNotFoundException, SQLException {
        Connection connection = null;

        // create Driver connect to MySQL
        Class.forName("com.mysql.cj.jdbc.Driver");

        // connect to database 'hr_management'
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hr_management", "root", "root");

        // create statement
        Statement statement = connection.createStatement();

        // delete data in database
        System.out.println("Delete records into the table...");

        String sql = "DELETE FROM department WHERE id = '" + id + "'";
        Integer res = statement.executeUpdate(sql);
        System.out.println("Delete records successfully!");
        return res;
    }
}
