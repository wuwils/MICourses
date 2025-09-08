package com.xiaomi.wusheng.work_0304.controller;

import com.xiaomi.wusheng.work_0304.model.KnowledgeBase;
import com.xiaomi.wusheng.work_0304.service.KnowledgeBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/knowledge")
public class KnowledgeBaseController {

    @Autowired
    private KnowledgeBaseService knowledgeBaseService;

    // 默认查询所有知识库记录
    @GetMapping
    public List<KnowledgeBase> getAllKnowledge() {
        return knowledgeBaseService.getAllKnowledge();
    }

    // 根据问题描述和关键字搜索知识库记录
    @GetMapping("/search")
    public List<KnowledgeBase> searchKnowledge(
            @RequestParam(required = false) String question,
            @RequestParam(required = false) String keywords) {
        return knowledgeBaseService.searchKnowledge(question, keywords);
    }
}
