package com.xiaomi.wusheng.work_0224.question_3;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class Main{
    public static void main(String[] args) throws InterruptedException{
        ConfigCenter configCenter = new ConfigCenter("src/main/resources/work_0224/question_3/config.json");
        configCenter.fileToConfig();

        ClientThread client1 = new ClientThread(configCenter);
        ClientThread client2 = new ClientThread(configCenter);

        // 客户端并发读取配置
        System.out.println("\n================================================");
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(client1::printCurrentConfig);
        executor.execute(client2::printCurrentConfig);
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);

        // 管理员线程进行全量配置更新
        AdminThread admin = new AdminThread(configCenter);
        Map<String, String> newConfig = new HashMap<>();
        newConfig.put("555", "EEE");
        newConfig.put("666", "FFF");
        System.out.println("\n管理员线程进行全量配置更新...");
        admin.updateConfig(newConfig);

        // 多客户端再次同时读取配置，验证配置是否已更新
        System.out.println("\n================================================");
        executor = Executors.newFixedThreadPool(2);
        executor.execute(client1::printCurrentConfig);
        executor.execute(client2::printCurrentConfig);
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);

        // 配置更新失败，自动回滚到前一版本
        System.out.println("\n配置更新失败，自动回滚到前一版本");
        Map<String, String> invalidConfig = new HashMap<>();
        invalidConfig.put(null, "GGG");
        admin.updateConfig(invalidConfig);

        // 多客户端再次同时读取配置，验证配置是否回滚到原版本
        System.out.println("\n================================================");
        executor = Executors.newFixedThreadPool(2);
        executor.execute(client1::printCurrentConfig);
        executor.execute(client2::printCurrentConfig);
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);

        // 配置持久化到文件
        System.out.println("\n配置持久化到文件");
        configCenter.configToFile();

        // 重启配置中心并加载持久化配置
        System.out.println("\n重启配置中心并加载持久化配置\n");
        ConfigCenter newConfigCenter = new ConfigCenter("src/main/resources/work_0224/question_3/config.json");
        newConfigCenter.fileToConfig();

        // 客户端读取重启后的配置
        System.out.println("\n================================================");
        ClientThread client3 = new ClientThread(newConfigCenter);
        client3.printCurrentConfig();
        client3.printCurrentVersion();
    }
}