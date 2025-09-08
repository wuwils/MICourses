package com.xiaomi.wusheng.course_0228.SpringBootZooKeeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws Exception {
        // 启动Spring Boot应用程序
        ApplicationContext context = SpringApplication.run(Application.class, args);

        // 获取ZooKeeperService实例
        ZooKeeperService zooKeeperService = context.getBean(ZooKeeperService.class);

        // 测试创建节点
        String path = "/test-node";
        String data = "Hello, ZooKeeper!";
        zooKeeperService.createNode(path, data);
        System.out.println("Node created: " + path);

        // 测试获取节点数据
        String nodeData = zooKeeperService.getNodeData(path);
        System.out.println("Node data: " + nodeData);

        // 测试更新节点数据
        String newData = "Updated data!";
        zooKeeperService.updateNodeData(path, newData);
        System.out.println("Node updated with new data: " + newData);

        // 测试删除节点
        zooKeeperService.deleteNode(path);
        System.out.println("Node deleted: " + path);
    }
}