package com.xiaomi.wusheng.service;

import com.xiaomi.wusheng.model.ChatResponse;
import reactor.core.publisher.Flux;

public interface ChatService {
    ChatResponse chat(String prompt);
    Flux<String> chatStream(String prompt);
}
