package com.xiaomi.wusheng.work_0304.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaomi.wusheng.work_0304.dto.ChatRequestDTO;
import com.xiaomi.wusheng.work_0304.model.ChatHistory;
import com.xiaomi.wusheng.work_0304.model.ChatResponse;
import com.xiaomi.wusheng.work_0304.service.ChatHistoryService;
import com.xiaomi.wusheng.work_0304.service.ChatService;
import com.xiaomi.wusheng.work_0304.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

    private final ObjectMapper mapper = new ObjectMapper();

    // 同步接口：生成会话ID并保存历史记录
    @PostMapping("/ask")
    public ChatResponse ask(@RequestBody ChatRequestDTO requestDTO) {
        // 使用数据库生成的会话ID
        Integer conversationId = conversationService.createConversation();
        String prompt = requestDTO.getPrompt();

        // 保存用户提问记录
        ChatHistory userHistory = new ChatHistory();
        userHistory.setConversationId(conversationId);
        userHistory.setUserNickname("用户");
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

        // 保存用户提问记录
        ChatHistory userHistory = new ChatHistory();
        userHistory.setConversationId(conversationId);
        userHistory.setUserNickname("用户");
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

        return streamFlux;
    }
}
