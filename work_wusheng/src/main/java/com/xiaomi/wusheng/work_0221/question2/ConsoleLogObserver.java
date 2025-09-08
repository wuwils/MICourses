package com.xiaomi.wusheng.work_0221.question2;

// 控制台日志观察者 根据日志级别添加颜色
public class ConsoleLogObserver implements LogObserver {
    private LogLevel level;
    private String message;
    public ConsoleLogObserver(LogLevel level){
        this.level = level;
        this.message = message;
    }

    @Override
    public void update(LogLevel level, String message) {
        String Message = getMessage(level, message);
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



