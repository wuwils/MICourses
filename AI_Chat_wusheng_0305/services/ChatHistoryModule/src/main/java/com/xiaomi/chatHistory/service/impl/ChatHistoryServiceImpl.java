package com.xiaomi.chatHistory.service.impl;

import com.xiaomi.chatHistory.dao.ChatHistoryMapper;
import com.xiaomi.chatHistory.model.ChatHistory;
import com.xiaomi.chatHistory.service.ChatHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ChatHistoryServiceImpl implements ChatHistoryService {

    @Autowired
    private ChatHistoryMapper chatHistoryMapper;

    // 使用 AtomicInteger 从 0 开始，每次调用 incrementAndGet() 得到1、2、3...等
    private static final AtomicInteger conversationIdGenerator = new AtomicInteger(0);

    @Override
    public Integer getNextConversationId() {
        return conversationIdGenerator.incrementAndGet();
    }

    @Override
    public boolean saveHistory(ChatHistory history) {
        return chatHistoryMapper.insert(history) > 0;
    }

    @Override
    public List<ChatHistory> queryHistory(String conversationId, String userNickname, String startTime, String endTime) {
        Integer convId = null;
        if(conversationId != null && !conversationId.isEmpty()){
            try {
                convId = Integer.valueOf(conversationId);
            } catch(NumberFormatException e) {
                // 无效的会话ID，返回空列表或忽略该条件
            }
        }
        return chatHistoryMapper.queryHistory(convId, userNickname, startTime, endTime);
    }
}
