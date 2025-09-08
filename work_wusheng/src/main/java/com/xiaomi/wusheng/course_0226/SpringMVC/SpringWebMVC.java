package com.xiaomi.wusheng.course_0226.SpringMVC;

import java.io.File;
import java.io.IOException;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class SpringWebMVC {

    public static void main(String[] args) throws LifecycleException, IOException {

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);

        // 创建 Web 应用上下文，并与 ServletContext 关联
        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();

        // 在创建 Context 前添加以下代码
        File workDir = new File(System.getProperty("user.home"), "tomcat-workdir");
        if (!workDir.exists()) {
            workDir.mkdirs();
            System.out.println("Tomcat 工作目录: " + workDir.getAbsolutePath());
        }
        Context tomcatContext = tomcat.addContext("", workDir.getAbsolutePath());
        appContext.setServletContext(tomcatContext.getServletContext());
        appContext.register(WebConfig.class);

        // 必须在添加 Servlet 前调用 refresh()！
        appContext.refresh();

        // 注册 DispatcherServlet
        DispatcherServlet servlet = new DispatcherServlet(appContext);
        Tomcat.addServlet(tomcatContext, "dispatcher", servlet);
        tomcatContext.addServletMappingDecoded("/*", "dispatcher");

        tomcat.getConnector(); // 必须调用此方法才会真正初始化 Connector
        tomcat.start();
        tomcat.getServer().await();
    }
}
