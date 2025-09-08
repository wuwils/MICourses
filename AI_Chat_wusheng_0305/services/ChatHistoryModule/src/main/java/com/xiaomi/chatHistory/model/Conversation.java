package com.xiaomi.chatHistory.model;

import java.time.LocalDateTime;

public class Conversation {
    private Integer id;
    private LocalDateTime startTime;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public LocalDateTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
}
