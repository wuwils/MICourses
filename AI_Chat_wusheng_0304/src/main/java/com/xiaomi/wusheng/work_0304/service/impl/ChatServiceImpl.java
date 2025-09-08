package com.xiaomi.wusheng.work_0304.service.impl;

import com.xiaomi.wusheng.work_0304.model.ChatRequest;
import com.xiaomi.wusheng.work_0304.model.ChatResponse;
import com.xiaomi.wusheng.work_0304.service.ChatService;
import com.xiaomi.wusheng.work_0304.service.RedisCacheService;
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

    public ChatServiceImpl(WebClient.Builder webClientBuilder, RedisCacheService redisCacheService) {
        this.webClient = webClientBuilder.build();
        this.redisCacheService = redisCacheService;
    }

    @Override
    public ChatResponse chat(String prompt) {
        // 同步调用不作修改，主要使用流式接口
        return null;
    }

    @Override
    public Flux<String> chatStream(String prompt) {
        // 先检查缓存
        String cachedAnswer = redisCacheService.getCachedAnswer(prompt);
        if (cachedAnswer != null) {
            // 直接返回缓存答案（这里返回单条消息）
            return Flux.just(cachedAnswer);
        }
        // 构造请求payload
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

        // 在后台累积完整答案后保存到缓存
        streamFlux.reduce("", (acc, cur) -> acc + cur)
                .publishOn(Schedulers.boundedElastic())
                .subscribe(finalAnswer -> {
                    // 将最终答案缓存到 Redis 中
                    redisCacheService.cacheAnswer(prompt, finalAnswer);
                }, error -> {
                    System.err.println("缓存问答结果失败：" + error.getMessage());
                });

        return streamFlux;
    }
}
