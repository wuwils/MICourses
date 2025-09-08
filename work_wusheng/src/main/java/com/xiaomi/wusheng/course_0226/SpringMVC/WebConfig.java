package com.xiaomi.wusheng.course_0226.SpringMVC;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan("com.xiaomi.wusheng.course_0226.SpringMVN")
@EnableWebMvc  // 激活MVC默认配置
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器
        registry.addInterceptor(new FirstInterceptor())
                .addPathPatterns("/**");
    }
}
