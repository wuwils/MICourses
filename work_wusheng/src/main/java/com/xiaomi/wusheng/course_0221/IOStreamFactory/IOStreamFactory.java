package com.xiaomi.wusheng.course_0221.IOStreamFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface IOStreamFactory {
    InputStream createInputStream() throws IOException;
    OutputStream createOutputStream() throws IOException;
}
