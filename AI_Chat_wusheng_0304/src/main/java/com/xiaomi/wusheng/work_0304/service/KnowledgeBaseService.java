package com.xiaomi.wusheng.work_0304.service;

import com.xiaomi.wusheng.work_0304.model.KnowledgeBase;
import java.util.List;

public interface KnowledgeBaseService {
    List<KnowledgeBase> getAllKnowledge();
    List<KnowledgeBase> searchKnowledge(String question, String keywords);
}
