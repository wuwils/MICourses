package com.xiaomi.wusheng.work_0304.controller;

import com.xiaomi.wusheng.work_0304.model.ChatHistory;
import com.xiaomi.wusheng.work_0304.service.ChatHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat/history")
public class ChatHistoryController {

    @Autowired
    private ChatHistoryService chatHistoryService;

    @GetMapping
    public List<ChatHistory> queryHistory(
            @RequestParam(required = false) String conversationId,
            @RequestParam(required = false) String userNickname,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime) {
        return chatHistoryService.queryHistory(conversationId, userNickname, startTime, endTime);
    }
}
