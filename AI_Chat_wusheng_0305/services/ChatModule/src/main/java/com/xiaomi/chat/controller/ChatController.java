package com.xiaomi.chat.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.xiaomi.chat.dto.ChatRequestDTO;
import com.xiaomi.chat.model.ChatResponse;
import com.xiaomi.chat.service.ChatService;
import com.xiaomi.chatHistory.model.ChatHistory;
import com.xiaomi.chatHistory.service.ChatHistoryService;
import com.xiaomi.chatHistory.service.ConversationService;
import com.xiaomi.knowledgeBase.service.KnowledgeBaseService;
import com.xiaomi.user.model.User;
import com.xiaomi.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatHistoryService chatHistoryService;

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private KnowledgeBaseService knowledgeBaseService;

    @Autowired
    private UserService userService;

    private final ObjectMapper mapper = new ObjectMapper();

    // 获取当前用户昵称
    private String getCurrentUserNickname() {
        // 从认证上下文获取当前用户的邮箱，并根据邮箱查询用户的实际昵称
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth: " + auth);
        User currentUser = (User) auth.getPrincipal();
        System.out.println("currentUser: " + currentUser);
        String nickname = currentUser.getUserName();
        System.out.println("nickname: " + nickname);
        return nickname;
    }

    // 同步接口：生成会话ID并保存历史记录
    @PostMapping("/ask")
    public ChatResponse ask(@RequestBody ChatRequestDTO requestDTO) {
        Integer conversationId = conversationService.createConversation();
        String prompt = requestDTO.getPrompt();
        // 获取当前用户昵称
        String nickname = getCurrentUserNickname();

        // 保存用户提问记录，使用实际昵称
        ChatHistory userHistory = new ChatHistory();
        userHistory.setConversationId(conversationId);
        userHistory.setUserNickname(nickname);
        userHistory.setUserType("user");
        userHistory.setTime(LocalDateTime.now());
        userHistory.setContent(prompt);
        chatHistoryService.saveHistory(userHistory);

        ChatResponse response = chatService.chat(prompt);

        // 保存客服回答记录
        if (response != null && response.getChoices() != null && !response.getChoices().isEmpty()) {
            String answer = response.getChoices().get(0).getMessage().getContent();
            ChatHistory botHistory = new ChatHistory();
            botHistory.setConversationId(conversationId);
            botHistory.setUserNickname("智能客服");
            botHistory.setUserType("assistant");
            botHistory.setTime(LocalDateTime.now());
            botHistory.setContent(answer);
            chatHistoryService.saveHistory(botHistory);
        }
        return response;
    }

    // 流式接口：生成会话ID并保存历史记录
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> stream(@RequestParam String prompt) {
        Integer conversationId = conversationService.createConversation();
        String nickname = getCurrentUserNickname();

        // 保存用户提问记录
        ChatHistory userHistory = new ChatHistory();
        userHistory.setConversationId(conversationId);
        userHistory.setUserNickname(nickname);
        userHistory.setUserType("user");
        userHistory.setTime(LocalDateTime.now());
        userHistory.setContent(prompt);
        chatHistoryService.saveHistory(userHistory);

        Flux<String> streamFlux = chatService.chatStream(prompt).cache();

        // 解析 SSE 数据，并累积完整答案后保存客服回答记录
        streamFlux
                .map(raw -> {
                    String line = raw.trim();
                    if (line.startsWith("data:")) {
                        line = line.substring(5).trim();
                    }
                    if (line.equals("[DONE]")) {
                        return "";
                    }
                    try {
                        JsonNode node = mapper.readTree(line);
                        JsonNode choices = node.get("choices");
                        if (choices != null && choices.isArray() && choices.size() > 0) {
                            JsonNode delta = choices.get(0).get("delta");
                            if (delta != null) {
                                JsonNode contentNode = delta.get("content");
                                if (contentNode != null) {
                                    return contentNode.asText();
                                }
                            }
                        }
                    } catch (Exception e) {
                        return "";
                    }
                    return "";
                })
                .filter(part -> !part.isEmpty())
                .reduce("", (acc, cur) -> acc + cur)
                .map(finalAnswer -> finalAnswer.replace("[DONE]", "").trim())
                .publishOn(Schedulers.boundedElastic())
                .subscribe(finalAnswer -> {
                    ChatHistory botHistory = new ChatHistory();
                    botHistory.setConversationId(conversationId);
                    botHistory.setUserNickname("智能客服");
                    botHistory.setUserType("assistant");
                    botHistory.setTime(LocalDateTime.now());
                    botHistory.setContent(finalAnswer);
                    chatHistoryService.saveHistory(botHistory);
                }, error -> {
                    System.err.println("保存问答历史失败：" + error.getMessage());
                });

        // 返回给前端时对每个数据块进行处理，过滤掉 "[DONE]" 字符串
        Flux<String> cleanedFlux = streamFlux.map(raw -> {
            String line = raw.trim();
            if (line.startsWith("data:")) {
                line = line.substring(5).trim();
            }
            return line.replace("[DONE]", "").trim();
        }).filter(s -> !s.isEmpty());

        return cleanedFlux;
    }

    @GetMapping("/match")
    public String matchKnowledge(@RequestParam String prompt) {
        String answer = knowledgeBaseService.matchKnowledge(prompt);
        return answer;
    }
}
