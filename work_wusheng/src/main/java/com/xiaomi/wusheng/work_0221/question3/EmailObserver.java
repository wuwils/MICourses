package com.xiaomi.wusheng.work_0221.question3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailObserver implements Observer {
    private ExecutorService executorService;

    public EmailObserver() {
        this.executorService = Executors.newCachedThreadPool();
    }

    @Override
    public void update(String eventType, String fileName) {
        executorService.submit(() -> {
            try {
                sendEmail(eventType, fileName);
            } catch (Exception e) {
                System.err.println("\n发送邮件失败: " + e.getMessage());
            }
        });
    }

    // 模拟发送邮件的过程
    private void sendEmail(String eventType, String fileName) throws Exception {
        String subject = "文件变动通知 - " + eventType;
        String content = "文件 \"" + fileName + "\" 被 " + eventType + "。\n请检查文件夹以确认变动。";
        System.out.println("\n发送邮件通知：\n主题: " + subject + "\n内容: " + content);

        // 模拟可能发生的错误
        if (Math.random() < 0.1) {
            throw new Exception("邮件发送失败: 邮件服务器未响应");
        }
    }

    public void shutdown() {
        executorService.shutdown();
    }
}
