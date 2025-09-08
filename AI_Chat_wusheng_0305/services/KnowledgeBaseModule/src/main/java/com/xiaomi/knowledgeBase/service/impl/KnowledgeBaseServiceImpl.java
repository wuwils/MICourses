package com.xiaomi.knowledgeBase.service.impl;

import com.xiaomi.knowledgeBase.dao.KnowledgeBaseMapper;
import com.xiaomi.knowledgeBase.model.KnowledgeBase;
import com.xiaomi.knowledgeBase.service.KnowledgeBaseService;
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

    @Override
    public boolean addKnowledge(KnowledgeBase kb) {
        return knowledgeBaseMapper.insert(kb) > 0;
    }

    @Override
    public boolean updateKnowledge(KnowledgeBase kb) {
        return knowledgeBaseMapper.update(kb) > 0;
    }

    @Override
    public boolean deleteKnowledge(Long id) {
        return knowledgeBaseMapper.delete(id) > 0;
    }

    @Override
    public String matchKnowledge(String prompt) {
        // 模糊匹配：这里简单使用 question 和 keywords 两字段进行搜索，全部转换为小写比较
        List<KnowledgeBase> list = knowledgeBaseMapper.search(prompt, prompt);
        if (list != null && !list.isEmpty()) {
            // 返回第一个匹配记录的答案（也可改为更复杂的匹配策略）
            return list.get(0).getAnswer();
        }
        return "";
    }
}
