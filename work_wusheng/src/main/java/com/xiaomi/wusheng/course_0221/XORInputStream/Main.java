package com.xiaomi.wusheng.course_0221.XORInputStream;

import java.io.*;

public class Main {

    public static void main(String[] args) {
        String inputFilePath = "src/main/java/com/xiaomi/wusheng/course_0221/IOTest/example.txt";
        String encryptedFilePath = "src/main/java/com/xiaomi/wusheng/course_0221/XORInputStream/encrypted.txt";
        String decryptedFilePath = "src/main/java/com/xiaomi/wusheng/course_0221/XORInputStream/decrypted.txt";
        byte key = 0x55;

        try {
            encryptFile(inputFilePath, encryptedFilePath, key);
            System.out.println("文件加密完成！");

            decryptFile(encryptedFilePath, decryptedFilePath, key);
            System.out.println("文件解密完成！");

            System.out.println("解密后的文件内容：");
            printFileContent(decryptedFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void encryptFile(String inputFilePath, String outputFilePath, byte key) throws IOException {
        try (InputStream inputStream = new FileInputStream(inputFilePath);
             XORInputStream xorInputStream = new XORInputStream(inputStream, key);
             OutputStream outputStream = new FileOutputStream(outputFilePath)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = xorInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }

    private static void decryptFile(String inputFilePath, String outputFilePath, byte key) throws IOException {
        try (InputStream inputStream = new FileInputStream(inputFilePath);
             XORInputStream xorInputStream = new XORInputStream(inputStream, key);
             OutputStream outputStream = new FileOutputStream(outputFilePath)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = xorInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }

    private static void printFileContent(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}
