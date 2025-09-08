package com.xiaomi.wusheng.course_0221.IOStreamFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

// 实现 ByteArrayStreamFactory 类
public class ByteArrayStreamFactory implements IOStreamFactory {
    private byte[] inputData;
    private ByteArrayOutputStream outputStream;

    public ByteArrayStreamFactory(byte[] inputData) {
        this.inputData = inputData;
        this.outputStream = new ByteArrayOutputStream();
    }

    @Override
    public InputStream createInputStream() throws IOException {
        return new ByteArrayInputStream(inputData);
    }

    @Override
    public OutputStream createOutputStream() throws IOException {
        return outputStream;
    }

    public byte[] getOutputData() {
        return outputStream.toByteArray();
    }
}