package com.ntl.database;

import java.sql.*;

public class JdbcData {
    public Statement getStatement() throws ClassNotFoundException, SQLException {
        Connection connection = null;

        // create Driver connect to MySQL
        Class.forName("com.mysql.cj.jdbc.Driver");

        // connect to database 'hr_management'
        connection = DriverManager.getConnection(InfoDB.url, InfoDB.username, InfoDB.password);

        // create statement
        Statement statement = connection.createStatement();

        return statement;
    }
}
