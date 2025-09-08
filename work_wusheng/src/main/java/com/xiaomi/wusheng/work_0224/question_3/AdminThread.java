package com.xiaomi.wusheng.work_0224.question_3;

import java.util.Map;
import java.util.concurrent.*;

public class AdminThread{
    private final ConfigCenter configCenter;
    public AdminThread(ConfigCenter configCenter){
        this.configCenter = configCenter;
    }

    public void updateConfig(Map<String, String> newConfig){
        try{
            boolean success = configCenter.updateConfig(newConfig);
            if (success) {
                System.out.println("\n配置更新成功");
            } else {
                System.out.println("\n配置更新失败，回滚到前一版本");
            }
        }catch(Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }

    private Map<String, String> generateNewConfig(){
        Map<String, String> newConfig = new ConcurrentHashMap<>();
        newConfig.put("123", "ABC" + System.currentTimeMillis());
        newConfig.put("234", "BCD" + System.currentTimeMillis());
        return newConfig;
    }

    public void startConfigUpdater(){
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            Map<String, String> newConfig = generateNewConfig();
            updateConfig(newConfig);
        }, 0, 30, TimeUnit.SECONDS);
    }
}