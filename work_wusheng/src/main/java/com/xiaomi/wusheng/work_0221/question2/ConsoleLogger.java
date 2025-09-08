package com.xiaomi.wusheng.work_0221.question2;

// 将日志输出到控制台，根据日志级别改变颜色
public class ConsoleLogger implements Logger {
    private LogLevel logLevel;

    public ConsoleLogger(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    @Override
    public void log(String message) {
        log(LogLevel.INFO, message);
    }

    public void log(LogLevel level, String message) {
        StringBuilder logMessage = new StringBuilder();
        logMessage.append("[").append(level).append("] ");
        logMessage.append(message);
        String Message = getMessage(level, logMessage.toString());
        System.out.println(Message);
    }

    private String getMessage(LogLevel level, String message) {
        switch (level) {
            case DEBUG:
                return "\u001B[36m" + message + "\u001B[0m"; // 青色
            case INFO:
                return "\u001B[32m" + message + "\u001B[0m"; // 绿色
            case WARN:
                return "\u001B[33m" + message + "\u001B[0m"; // 黄色
            case ERROR:
                return "\u001B[31m" + message + "\u001B[0m"; // 红色
            default:
                return message;
        }
    }
}
