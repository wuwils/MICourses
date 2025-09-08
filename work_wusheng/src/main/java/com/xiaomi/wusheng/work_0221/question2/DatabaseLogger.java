package com.xiaomi.wusheng.work_0221.question2;

import java.text.SimpleDateFormat;
import java.util.Date;

// 模拟将日志输出到数据库
public class DatabaseLogger implements Logger {
    private String databaseUrl = "jdbc:mysql://localhost:3306/logdb";
    private String databaseUser = "root";
    private String databasePassword = "password";

    @Override
    public void log(String message) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String logMessage = timestamp + " - INFO: " + message;
        saveToDatabase(logMessage);
    }

    // 模拟将日志消息插入数据库
    private void saveToDatabase(String message) {
        System.out.println("连接到数据库: " + databaseUrl);
        System.out.println("执行 SQL 插入: INSERT INTO logs (log_message) VALUES ('" + message + "')");
    }
}

