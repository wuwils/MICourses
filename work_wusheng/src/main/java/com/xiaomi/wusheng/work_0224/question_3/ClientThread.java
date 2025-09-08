package com.xiaomi.wusheng.work_0224.question_3;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ClientThread{
    private final ConfigCenter configCenter;
    public ClientThread(ConfigCenter configCenter){
        this.configCenter = configCenter;
    }

    public void printCurrentConfig(){
        try{
            Map<String, String> config = configCenter.getCurrentConfig();
            if (config.isEmpty()){
                System.out.println("\n配置为空");
            }else{
                System.out.println("\n配置内容：");
                config.forEach((key, value) -> System.out.println(key + " = " + value));
            }
        }catch(Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void printCurrentVersion(){
        try{
            int version = configCenter.getCurrentVersion();
            System.out.println("\n配置版本: " + version);
        }catch(Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void startConfigMonitor(){
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            printCurrentConfig();
            printCurrentVersion();
        }, 0, 10, TimeUnit.SECONDS);
    }
}