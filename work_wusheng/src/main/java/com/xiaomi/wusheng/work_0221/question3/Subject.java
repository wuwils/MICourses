package com.xiaomi.wusheng.work_0221.question3;

import java.util.ArrayList;
import java.util.List;

public interface Subject {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(String eventType, String fileName);
}

