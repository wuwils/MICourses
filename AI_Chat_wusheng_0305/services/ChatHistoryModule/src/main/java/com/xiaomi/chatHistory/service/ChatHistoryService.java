package com.xiaomi.chatHistory.service;

import com.xiaomi.chatHistory.model.ChatHistory;
import java.util.List;

public interface ChatHistoryService {
    boolean saveHistory(ChatHistory history);
    List<ChatHistory> queryHistory(String conversationId, String userNickname, String startTime, String endTime);
    Integer getNextConversationId();
}
