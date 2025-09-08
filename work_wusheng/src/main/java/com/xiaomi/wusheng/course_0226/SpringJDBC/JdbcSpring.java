package com.xiaomi.wusheng.course_0226.SpringJDBC;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("com.xiaomi.wusheng.course_0226.SpringJDBC")
@Import(JdbcConfig.class)
public class JdbcSpring {

    public static void main(String[] args) {
        // 创建 ApplicationContext 实例
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(JdbcSpring.class);
        Connection conn = applicationContext.getBean("createConnection", Connection.class);
        // statement代表一个SQL语句，可以执行查询和更新等操作，使用后要及时释放。
        try (Statement stmt = conn.createStatement()) {
            // 执行SQL...
            stmt.execute("SELECT VERSION()");
            ResultSet rs = stmt.getResultSet();
            rs.next();
            System.out.println(rs.getString(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
