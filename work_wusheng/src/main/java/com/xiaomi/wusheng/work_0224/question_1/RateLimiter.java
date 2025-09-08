package com.xiaomi.wusheng.work_0224.question_1;

import java.util.concurrent.atomic.AtomicInteger;

// 令牌桶限流
public class RateLimiter{
    private int capacity;
    private AtomicInteger tokens;
    private long lastRefillTime;
    private final long refillInterval;

    public RateLimiter(int ratePerSecond){
        this.capacity = ratePerSecond;
        this.tokens = new AtomicInteger(ratePerSecond);
        this.lastRefillTime = System.currentTimeMillis();
        this.refillInterval = 1000;
    }

    public boolean tryAcquire(){
        refillTokens();
        int currentTokens = tokens.get();
        while (currentTokens > 0){
            if (tokens.compareAndSet(currentTokens, currentTokens - 1)){
                return true;
            }
            currentTokens = tokens.get();
        }
        return false;
    }

    // 补充令牌
    private void refillTokens(){
        long now = System.currentTimeMillis();
        if (now - lastRefillTime > refillInterval){
            tokens.set(capacity);
            lastRefillTime = now;
        }
    }
}
