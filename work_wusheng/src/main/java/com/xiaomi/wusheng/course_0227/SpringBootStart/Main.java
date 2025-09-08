package com.xiaomi.wusheng.course_0227.SpringBootStart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import java.util.Enumeration;

@RestController
@SpringBootApplication
public class Main {
    @Autowired
    TestSpring test;

    @Autowired
    Environment environment;

    @Autowired
    ServletContext servletContext;

    @RequestMapping("/")
    public String home() {
        test.print();

        // 检查配置文件
        String[] activeProfiles = environment.getActiveProfiles();
        for (String profile : activeProfiles) {
            System.out.println(profile);
        }
        String config = environment.getProperty("config", "404");
        System.out.println(config);

        // 检查嵌入式服务器
        Enumeration<String> attributeNames = servletContext.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            if (attributeName.contains("org.eclipse.jetty")) {
                System.out.println("Using Jetty");
            } else if (attributeName.contains("org.apache.catalina")) {
                System.out.println("Using Tomcat");
            }
        }

        return "Hello Spring!";
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
        TestSpring bean = context.getBean(TestSpring.class);
        System.out.println("SpringBoot Started\n");
        bean.print();

        // 关闭上下文（可选）
        // context.close();
    }
}
