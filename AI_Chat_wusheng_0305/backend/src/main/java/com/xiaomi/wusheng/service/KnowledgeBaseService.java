package com.xiaomi.wusheng.service;

import com.xiaomi.wusheng.model.KnowledgeBase;
import java.util.List;

public interface KnowledgeBaseService {
    List<KnowledgeBase> getAllKnowledge();
    List<KnowledgeBase> searchKnowledge(String question, String keywords);

    // 后台管理操作
    boolean addKnowledge(KnowledgeBase kb);
    boolean updateKnowledge(KnowledgeBase kb);
    boolean deleteKnowledge(Long id);

    // 用户提问匹配知识库，返回最佳匹配答案（若无匹配返回空字符串）
    String matchKnowledge(String prompt);
}
