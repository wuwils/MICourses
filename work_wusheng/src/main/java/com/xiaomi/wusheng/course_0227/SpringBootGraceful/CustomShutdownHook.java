package com.xiaomi.wusheng.course_0227.SpringBootGraceful;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

@Component
public class CustomShutdownHook implements DisposableBean {

    @Override
    public void destroy() throws Exception {
        // 自定义停机逻辑
        System.out.println("执行自定义停机逻辑：释放资源、关闭连接等...");
    }
}
