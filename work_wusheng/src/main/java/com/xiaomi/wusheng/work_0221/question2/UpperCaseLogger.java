package com.xiaomi.wusheng.work_0221.question2;

// 将日志消息转换为大写
public class UpperCaseLogger implements Logger {
    private Logger logger;

    public UpperCaseLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void log(String message) {
        logger.log(message.toUpperCase());
    }
}
