package com.xiaomi.wusheng.service.impl;

import com.xiaomi.wusheng.dao.ConversationMapper;
import com.xiaomi.wusheng.model.Conversation;
import com.xiaomi.wusheng.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversationServiceImpl implements ConversationService {

    @Autowired
    private ConversationMapper conversationMapper;

    @Override
    public Integer createConversation() {
        Conversation conversation = new Conversation();
        conversationMapper.insertConversation(conversation);
        return conversation.getId();  // 获取自增生成的会话ID
    }
}
