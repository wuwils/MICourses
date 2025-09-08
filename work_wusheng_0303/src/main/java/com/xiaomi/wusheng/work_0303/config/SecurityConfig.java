package com.xiaomi.wusheng.work_0303.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/index.html", "/register", "/login", "/api/users/register", "/api/users/login") //无需身份验证即可访问
                .permitAll()
                .anyRequest().authenticated() //其他所有请求都需要身份认证后才能访问
                .and()
                .formLogin().disable()
                .logout().permitAll(); // 允许所有用户访问注销接口
        return http.build();
    }

}
