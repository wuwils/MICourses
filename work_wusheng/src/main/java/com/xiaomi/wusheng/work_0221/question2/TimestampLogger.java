package com.xiaomi.wusheng.work_0221.question2;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// 在日志消息前添加时间戳
public class TimestampLogger implements Logger {
    private Logger logger;

    public TimestampLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void log(String message) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        logger.log("[" + timestamp + "] " + message);
    }

}
