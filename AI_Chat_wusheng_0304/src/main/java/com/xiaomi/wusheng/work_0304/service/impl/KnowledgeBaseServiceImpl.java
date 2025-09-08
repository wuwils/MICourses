package com.xiaomi.wusheng.work_0304.service.impl;

import com.xiaomi.wusheng.work_0304.dao.KnowledgeBaseMapper;
import com.xiaomi.wusheng.work_0304.model.KnowledgeBase;
import com.xiaomi.wusheng.work_0304.service.KnowledgeBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class KnowledgeBaseServiceImpl implements KnowledgeBaseService {

    @Autowired
    private KnowledgeBaseMapper knowledgeBaseMapper;

    @Override
    public List<KnowledgeBase> getAllKnowledge() {
        return knowledgeBaseMapper.findAll();
    }

    @Override
    public List<KnowledgeBase> searchKnowledge(String question, String keywords) {
        return knowledgeBaseMapper.search(question, keywords);
    }
}
