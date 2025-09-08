package com.xiaomi.wusheng.work_0221.question2;

import java.util.regex.Pattern;

// 新增实用功能，敏感信息过滤装饰器
public class FilterLogger implements Logger {
    private Logger logger;
    private Pattern[] Patterns;

    public FilterLogger(Logger logger) {
        this.logger = logger;
        this.Patterns = new Pattern[]{
                Pattern.compile("(password|pwd)=\\w+"), // 密码
                Pattern.compile("\\d{11}"), // 手机号
                Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}") // 邮箱
        };
    }

    @Override
    public void log(String message) {
        String filteredMessage = filter(message);
        logger.log(filteredMessage);
    }

    private String filter(String message) {
        for (Pattern pattern : Patterns) {
            message = pattern.matcher(message).replaceAll("***");
        }
        return message;
    }
}
