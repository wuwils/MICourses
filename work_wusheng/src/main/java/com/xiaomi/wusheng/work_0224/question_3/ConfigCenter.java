package com.xiaomi.wusheng.work_0224.question_3;

import java.io.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.*;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConfigCenter{
    private final Map<String, String> currentConfig = new ConcurrentHashMap<>();
    private final Map<String, String> previousConfig = new ConcurrentHashMap<>(); // 备份配置
    private final AtomicInteger currentVersion = new AtomicInteger(0);
    private final AtomicInteger previousVersion = new AtomicInteger(0); // 备份版本号
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final String filePath;

    public ConfigCenter(String filePath){
        this.filePath = filePath;
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::configToFile, 0, 5, TimeUnit.MINUTES); // 每隔5分钟保存配置
        fileToConfig();
    }

    // 获取当前配置
    public Map<String, String> getCurrentConfig(){
        readLock.lock();
        try {
            return new HashMap<>(currentConfig);
        } finally {
            readLock.unlock();
        }
    }

    // 更新配置
    public boolean updateConfig(Map<String, String> newConfig){
        writeLock.lock();
        try {
            // 备份当前配置和版本号
            backupCurrentConfig();

            // 更新版本号
            int newVersion = currentVersion.incrementAndGet();

            // 更新配置
            currentConfig.putAll(newConfig);

            System.out.println("配置更新成功，新版本: " + newVersion);
            return true;
        } catch (Exception e) {
            System.err.println("配置更新失败: " + e.getMessage());
            rollback();
            return false;
        } finally {
            writeLock.unlock();
        }
    }

    // 备份当前配置和版本号
    private void backupCurrentConfig(){
        previousConfig.clear();
        previousConfig.putAll(currentConfig);
        previousVersion.set(currentVersion.get());
    }

    // 回滚到前一版本
    private void rollback(){
        writeLock.lock();
        try {
            currentConfig.clear();
            currentConfig.putAll(previousConfig);
            currentVersion.set(previousVersion.get());

            System.out.println("回滚到前一版本，版本号：" + previousVersion.get());
        } finally {
            writeLock.unlock();
        }
    }

    // 配置持久化到文件
    void configToFile(){
        writeLock.lock();
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), currentConfig);
            System.out.println("\n配置持久化到文件: " + filePath);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            writeLock.unlock();
        }
    }

    // 文件读取配置
    public void fileToConfig(){
        writeLock.lock();
        try {
            File file = new File(filePath);
            if (file.exists()) {
                Map<String, String> loadedConfig = objectMapper.readValue(file, Map.class);
                currentConfig.clear();
                currentConfig.putAll(loadedConfig);
                System.out.println("\n读取配置文件: " + filePath);
            } else {
                System.out.println("\n配置文件不存在");
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            writeLock.unlock();
        }
    }

    // 获取版本号
    public int getCurrentVersion(){
        return currentVersion.get();
    }
}