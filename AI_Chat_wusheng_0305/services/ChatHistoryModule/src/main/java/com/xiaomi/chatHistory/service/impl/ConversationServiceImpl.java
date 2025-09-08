package com.xiaomi.chatHistory.service.impl;

import com.xiaomi.chatHistory.dao.ConversationMapper;
import com.xiaomi.chatHistory.model.Conversation;
import com.xiaomi.chatHistory.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversationServiceImpl implements ConversationService {

    @Autowired
    private ConversationMapper conversationMapper;

    @Override
    public Integer createConversation() {
        Conversation conversation = new Conversation();
        // 自动设置 start_time via SQL 的 NOW()
        conversationMapper.insertConversation(conversation);
        return conversation.getId();  // 获取自增生成的会话ID
    }
}
