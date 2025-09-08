package com.xiaomi.wusheng.course_0225;

import java.sql.*;

public class JDBCDemo {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/test";
    private static final String JDBC_USER = "test";
    private static final String JDBC_PASSWORD = "password";

    public static void main(String[] args) throws Exception {
        try(Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)){
            try(Statement stat = conn.createStatement()) {
//                String sql = "INSERT INTO students(name, age, grade) VALUES ('Tom', 20, 'A');";
//                String sql = "DELETE FROM students WHERE name = 'Tom'";
//                int result = stat.executeUpdate(sql);
//                if(result > 0){
//                    System.out.println("success");
//                }else {
//                    System.out.println("error");
//                }
//


//                String sql = "SELECT * FROM students WHERE age = 20";
//                try (ResultSet resultSet = stat.executeQuery(sql)) {
//                    while (resultSet.next()) {
//                        String name = resultSet.getString("name");
//                        int age = resultSet.getInt("age");
//                        String grade = resultSet.getString("grade");
//                        System.out.println("姓名:" + name + "年龄: " + age + "成镇: " + grade);
//                    }
//                }

                String sql = "SELECT * FROM students WHERE age = ?";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, 20);
                    try(ResultSet resultSet = ps.executeQuery()){
                        while (resultSet.next()) {
                            String name = resultSet.getString("name");
                            int age = resultSet.getInt("age");
                            String grade = resultSet.getString("grade");
                            System.out.println("姓名:" + name + "年龄: " + age + "成镇: " + grade);
                        }
                    }
                }
            }
        }
    }
}
