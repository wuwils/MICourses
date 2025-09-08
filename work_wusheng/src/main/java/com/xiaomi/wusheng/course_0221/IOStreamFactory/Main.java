package com.xiaomi.wusheng.course_0221.IOStreamFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Main {
    public static void main(String[] args) {
        try {
            IOStreamFactory fileFactory = new FileStreamFactory(
                    "src/main/java/com/xiaomi/wusheng/course_0221/IOTest/example.txt",
                    "src/main/java/com/xiaomi/wusheng/course_0221/IOStreamFactory/output.txt");
            performIOOperations(fileFactory);

            // 使用 ByteArrayStreamFactory 进行字节数组读写
            byte[] inputData = "Hello, ByteArray!".getBytes();
            ByteArrayStreamFactory byteArrayFactory = new ByteArrayStreamFactory(inputData);
            performIOOperations(byteArrayFactory);

            // 打印字节数组输出
            System.out.println("ByteArray 输出: " + new String(byteArrayFactory.getOutputData()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void performIOOperations(IOStreamFactory factory) throws IOException {
        // 创建输入流和输出流
        try (InputStream inputStream = factory.createInputStream();
             OutputStream outputStream = factory.createOutputStream()) {

            // 读取输入流并写入输出流
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            System.out.println("IO 操作完成！");
        }
    }
}