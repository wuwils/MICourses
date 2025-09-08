package com.xiaomi.wusheng.work_0221.question2;

// 日志观察者接口
public interface LogObserver {
    void update(LogLevel level, String message);
}
