package com.xiaomi.wusheng.course_0221.XORInputStream;

import java.io.IOException;
import java.io.InputStream;

class XORInputStream extends InputStream {
    private InputStream inputStream;
    private byte key;

    public XORInputStream(InputStream inputStream, byte key) {
        this.inputStream = inputStream;
        this.key = key;
    }

    @Override
    public int read() throws IOException {
        int data = inputStream.read();
        if (data == -1) {
            return -1;
        }
        return data ^ key;
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
    }
}
