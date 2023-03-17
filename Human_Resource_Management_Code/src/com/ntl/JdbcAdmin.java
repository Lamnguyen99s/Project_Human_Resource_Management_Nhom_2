package com.ntl;

import java.sql.*;

public class JdbcAdmin {
    // check login
    public ResultSet checkAdmin() throws ClassNotFoundException, SQLException {
        Connection connection = null;

        // create Driver connect to MySQL
        Class.forName("com.mysql.cj.jdbc.Driver");

        // connect to database 'hr_management'
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hr_management", "root", "root");

        // create statement
        Statement statement = connection.createStatement();

        // get data from table 'admin'
        ResultSet resultSet = statement.executeQuery("SELECT * FROM admin");
        return resultSet;
    }
}
