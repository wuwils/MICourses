package com.xiaomi.wusheng.course_0221.FileCopy;

import java.io.*;
import java.util.Scanner;

public class FileCopyTest {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 1. 允许用户输入源文件路径和目标文件路径
        System.out.print("请输入源文件路径: ");
        String sourcePath = scanner.nextLine();
        System.out.print("请输入目标文件路径: ");
        String targetPath = scanner.nextLine();

        File sourceFile = new File(sourcePath);
        File targetFile = new File(targetPath);

        // 检查源文件是否存在
        if (!sourceFile.exists()) {
            System.out.println("源文件不存在！");
            return;
        }

        // 2. 根据文件类型选择适当的流
        try {
            long startTime = System.currentTimeMillis(); // 记录开始时间

            if (isTextFile(sourcePath)) {
                copyTextFile(sourceFile, targetFile); // 复制文本文件
            } else {
                copyBinaryFile(sourceFile, targetFile); // 复制二进制文件
            }

            long endTime = System.currentTimeMillis(); // 记录结束时间
            System.out.println("文件复制完成！");
            System.out.println("耗时: " + (endTime - startTime) + " 毫秒");

            // 4. 读取并打印 example.txt 的内容
            if (targetPath.endsWith("example.txt")) {
                System.out.println("\n目标文件内容如下：");
                printFileContent(targetFile);
            }

        } catch (IOException e) {
            System.out.println("文件复制失败: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static boolean isTextFile(String filePath) {
        String extension = filePath.substring(filePath.lastIndexOf(".") + 1).toLowerCase();
        return extension.equals("txt") || extension.equals("java") || extension.equals("xml");
    }

    private static void copyTextFile(File sourceFile, File targetFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(targetFile))) {

            char[] buffer = new char[1024];
            int bytesRead;
            long totalBytesRead = 0;
            long fileSize = sourceFile.length();

            while ((bytesRead = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, bytesRead);
                totalBytesRead += bytesRead;
                printProgress(totalBytesRead, fileSize); // 显示复制进度
            }
        }
    }

    private static void copyBinaryFile(File sourceFile, File targetFile) throws IOException {
        try (FileInputStream fis = new FileInputStream(sourceFile);
             FileOutputStream fos = new FileOutputStream(targetFile)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            long totalBytesRead = 0;
            long fileSize = sourceFile.length();

            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
                totalBytesRead += bytesRead;
                printProgress(totalBytesRead, fileSize); // 显示复制进度
            }
        }
    }

    private static void printProgress(long bytesRead, long totalBytes) {
        double progress = (double) bytesRead / totalBytes * 100;
        System.out.printf("复制进度: %.2f%%\n", progress);
    }

    private static void printFileContent(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}
