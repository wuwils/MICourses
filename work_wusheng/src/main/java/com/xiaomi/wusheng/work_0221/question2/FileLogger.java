package com.xiaomi.wusheng.work_0221.question2;

import java.io.FileWriter;
import java.io.IOException;

// 将日志输出到文件
public class FileLogger implements Logger {
    private String filePath;

    public FileLogger(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void log(String message) {
        try (FileWriter filewriter = new FileWriter(filePath)) {
            filewriter.write("日志:\n " + message + "\n");
        } catch (IOException e) {
            System.err.println("错误！ 日志输出到文件失败: " + e.getMessage());
        }
    }
}
