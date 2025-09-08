package com.xiaomi.wusheng.work_0221.question2;

import java.util.ArrayList;
import java.util.List;

// 日志主题
public class LogSubject {
    private List<LogObserver> observers = new ArrayList<>();

    public void addObserver(LogObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(LogObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(LogLevel level, String message) {
        for (LogObserver observer : observers) {
            observer.update(level, message);
        }
    }
}
