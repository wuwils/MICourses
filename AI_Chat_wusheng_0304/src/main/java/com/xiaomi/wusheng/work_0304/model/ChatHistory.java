package com.xiaomi.wusheng.work_0304.model;

import java.time.LocalDateTime;

public class ChatHistory {
    private Long id;
    private Integer conversationId; // 改为 Integer
    private String userNickname;
    private String userType;
    private LocalDateTime time;
    private String content;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Integer getConversationId() {
        return conversationId;
    }
    public void setConversationId(Integer conversationId) {
        this.conversationId = conversationId;
    }
    public String getUserNickname() {
        return userNickname;
    }
    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }
    public String getUserType() {
        return userType;
    }
    public void setUserType(String userType) {
        this.userType = userType;
    }
    public LocalDateTime getTime() {
        return time;
    }
    public void setTime(LocalDateTime time) {
        this.time = time;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
