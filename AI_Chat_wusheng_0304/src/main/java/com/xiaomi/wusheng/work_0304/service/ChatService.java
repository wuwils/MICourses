package com.xiaomi.wusheng.work_0304.service;

import com.xiaomi.wusheng.work_0304.model.ChatResponse;
import reactor.core.publisher.Flux;

public interface ChatService {
    ChatResponse chat(String prompt);
    Flux<String> chatStream(String prompt);
}
