package com.xiaomi.wusheng.work_0221.question2;

import java.io.FileWriter;
import java.io.IOException;

// 文件日志观察者
public class FileLogObserver implements LogObserver {
    private String filePath;

    public FileLogObserver(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void update(LogLevel level, String message) {
        try (FileWriter filewriter = new FileWriter(filePath, true)) {
            filewriter.write("日志输出到文件 [" + level + "]: " + message + "\n");
        } catch (IOException e) {
            System.err.println("错误！ 日志输出到文件失败: " + e.getMessage());
        }
    }
}
