package com.xiaomi.wusheng.work_0221.question3;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String folderPath = "src/main/resources/work_0221/question3/observer/";

        try {
            FolderObserver folderObserver = new FolderObserver(folderPath);
            Observer loggerObserver = new LoggerObserver();
            Observer emailObserver = new EmailObserver();
            folderObserver.registerObserver(loggerObserver);
            folderObserver.registerObserver(emailObserver);

            Thread monitorThread = new Thread(() -> {
                try {
                    folderObserver.startObserver();
                } catch (IOException e) {
                    System.err.println("文件监控启动失败: " + e.getMessage());
                }
            });

            monitorThread.start();
            monitorThread.join();

        } catch (InterruptedException e) {
            System.err.println("程序异常退出: " + e.getMessage());
        }
    }
}