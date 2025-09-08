package com.xiaomi.chat.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaomi.chat.model.ChatRequest;
import com.xiaomi.chat.model.ChatResponse;
import com.xiaomi.chat.service.ChatService;
import com.xiaomi.chat.service.RedisCacheService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import java.util.Collections;

@Service
public class ChatServiceImpl implements ChatService {

    @Value("${chatglm.api.url}")
    private String chatUrl;

    @Value("${chatglm.api.key}")
    private String apiKey;

    private final WebClient webClient;
    private final RedisCacheService redisCacheService;
    private final ObjectMapper mapper;

    public ChatServiceImpl(WebClient.Builder webClientBuilder, RedisCacheService redisCacheService, ObjectMapper mapper) {
        this.webClient = webClientBuilder.build();
        this.redisCacheService = redisCacheService;
        this.mapper = mapper;
    }

    @Override
    public ChatResponse chat(String prompt) {
        // 同步调用不作修改，主要使用流式接口
        return null;
    }

    @Override
    public Flux<String> chatStream(String prompt) {
        // 先尝试从 Redis 获取缓存答案
        try {
            String cachedAnswer = redisCacheService.getCachedAnswer(prompt);
            if (cachedAnswer != null) {
                return Flux.just(cachedAnswer);
            }
        } catch (Exception e) {
            System.err.println("Redis 查询异常：" + e.getMessage());
            // 不影响后续调用
        }

        // 构造请求 payload
        ChatRequest.Message userMessage = new ChatRequest.Message();
        userMessage.setRole("user");
        userMessage.setContent(prompt);

        ChatRequest requestPayload = new ChatRequest();
        requestPayload.setModel("glm-4-plus");
        requestPayload.setMessages(Collections.singletonList(userMessage));
        requestPayload.setTemperature(0.95);
        requestPayload.setTop_p(0.70);
        requestPayload.setDo_sample(true);
        requestPayload.setStream(true);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        Flux<String> streamFlux = webClient.post()
                .uri(chatUrl)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .bodyValue(requestPayload)
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(String.class)
                .cache();

        // 累积完整答案后保存到 Redis
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
                .map(finalAnswer -> finalAnswer.replaceAll("\\[DONE\\]", "").trim()) // 去除可能存在的 [DONE]
                .publishOn(Schedulers.boundedElastic())
                .subscribe(finalAnswer -> {
                    // 保存经过处理的纯文本答案到 Redis
                    redisCacheService.cacheAnswer(prompt, finalAnswer);
                }, error -> {
                    System.err.println("缓存问答结果失败：" + error.getMessage());
                });


        return streamFlux;
    }

}
