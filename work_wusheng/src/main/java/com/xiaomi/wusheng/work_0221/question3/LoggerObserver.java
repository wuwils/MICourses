package com.xiaomi.wusheng.work_0221.question3;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoggerObserver implements Observer {
    private static final String LOG_FILE = "src/main/resources/work_0221/question3/log.txt";
    private ExecutorService executorService;

    public LoggerObserver() {
        executorService = Executors.newCachedThreadPool();
    }

    @Override
    public void update(String eventType, String fileName) {
        executorService.submit(() -> {
            try {
                logToFile(eventType, fileName);
            } catch (IOException e) {
                System.err.println("\n日志记录失败: " + e.getMessage());
            }
        });
    }

    private String formatLogMessage(String eventType, String fileName) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return String.format("[%s] [INFO] 文件 %s 被 %s", timestamp, fileName, eventType);
    }

    private void logToFile(String eventType, String fileName) throws IOException {
        String logMessage = formatLogMessage(eventType, fileName);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            writer.write(logMessage);
            writer.newLine();
            System.out.println("\n日志已写入: " + logMessage);
        }
    }

    public void shutdown() {
        executorService.shutdown();
    }
}
