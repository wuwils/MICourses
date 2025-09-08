package com.xiaomi.chatHistory.controller;

import com.xiaomi.chatHistory.model.ChatHistory;
import com.xiaomi.chatHistory.service.ChatHistoryService;
import com.xiaomi.user.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat/history")
public class ChatHistoryController {

    private final ChatHistoryService chatHistoryService;

    public ChatHistoryController(ChatHistoryService chatHistoryService) {
        this.chatHistoryService = chatHistoryService;
    }

    @GetMapping
    public List<ChatHistory> queryHistory(
            @RequestParam(required = false) String conversationId,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime) {
        // 获取当前认证用户信息
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        String nickname = currentUser.getUserName();

        // 只查询当前用户的聊天历史，忽略前端传入的 userNickname 参数
        return chatHistoryService.queryHistory(conversationId, nickname, startTime, endTime);
    }
}
