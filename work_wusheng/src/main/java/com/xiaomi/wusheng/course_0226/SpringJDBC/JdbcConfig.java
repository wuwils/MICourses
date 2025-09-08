package com.xiaomi.wusheng.course_0226.SpringJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/course_0226/jdbc.properties")
public class JdbcConfig {

    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;

    @Bean("createConnection")
    public Connection createConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

}
