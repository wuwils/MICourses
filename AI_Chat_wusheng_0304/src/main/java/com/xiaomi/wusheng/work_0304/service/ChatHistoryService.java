package com.xiaomi.wusheng.work_0304.service;

import com.xiaomi.wusheng.work_0304.model.ChatHistory;
import java.util.List;

public interface ChatHistoryService {
    boolean saveHistory(ChatHistory history);
    List<ChatHistory> queryHistory(String conversationId, String userNickname, String startTime, String endTime);
    Integer getNextConversationId();
}
