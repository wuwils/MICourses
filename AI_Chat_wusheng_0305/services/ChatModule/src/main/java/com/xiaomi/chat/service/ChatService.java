package com.xiaomi.chat.service;

import com.xiaomi.chat.model.ChatResponse;
import reactor.core.publisher.Flux;

public interface ChatService {
    ChatResponse chat(String prompt);
    Flux<String> chatStream(String prompt);
}
