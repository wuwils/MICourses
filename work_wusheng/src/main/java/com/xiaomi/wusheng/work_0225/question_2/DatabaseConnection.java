package com.xiaomi.wusheng.work_0225.question_2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection{
    public static final String JDBC_URL = "jdbc:mysql://localhost:3306/mydatabase";
    public static final String JDBC_USER = "shawn";
    public static final String JDBC_PASSWORD = "Shawn441";

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }
}
