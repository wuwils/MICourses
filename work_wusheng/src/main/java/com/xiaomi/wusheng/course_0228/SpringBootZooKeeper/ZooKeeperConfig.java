package com.xiaomi.wusheng.course_0228.SpringBootZooKeeper;

import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class ZooKeeperConfig {

    @Value("${zookeeper.address}")
    private String zookeeperAddress;

    @Value("${zookeeper.session-timeout}")
    private int sessionTimeout;

    @Bean
    public ZooKeeper zooKeeper() throws IOException {
        // 创建ZooKeeper客户端
        return new ZooKeeper(zookeeperAddress, sessionTimeout, event -> {
            if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                System.out.println("ZooKeeper connected successfully!");
            }
        });
    }
}
