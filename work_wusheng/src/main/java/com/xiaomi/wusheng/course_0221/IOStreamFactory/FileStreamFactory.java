package com.xiaomi.wusheng.course_0221.IOStreamFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

// 实现 FileStreamFactory 类
public class FileStreamFactory implements IOStreamFactory {
    private String inputFilePath;
    private String outputFilePath;

    public FileStreamFactory(String inputFilePath, String outputFilePath) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
    }

    @Override
    public InputStream createInputStream() throws IOException {
        return new FileInputStream(inputFilePath);
    }

    @Override
    public OutputStream createOutputStream() throws IOException {
        return new FileOutputStream(outputFilePath);
    }
}
