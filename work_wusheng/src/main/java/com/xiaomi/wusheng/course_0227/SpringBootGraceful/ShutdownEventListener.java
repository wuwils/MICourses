package com.xiaomi.wusheng.course_0227.SpringBootGraceful;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

@Component
public class ShutdownEventListener implements ApplicationListener<ContextClosedEvent> {

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        // 在应用程序关闭时执行逻辑
        System.out.println("应用程序正在关闭，执行清理操作...");
    }
}