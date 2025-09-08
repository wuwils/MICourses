/**
 * 基于Ubuntu20.04，命令行输入：
 *  1、mvn clean compile
 *  2、mvn exec:java -Dexec.mainClass="com.xiaomi.wusheng.work_0221.question1.Main"
 *    -Dexec.args="src/main/resources/work_0221/question1/oldFile.txt src/main/resources/work_0221/question1/newFile.txt Java Python"
 *
 *  结果保存在 src/main/resources/work_0221/question1
 * */

package com.xiaomi.wusheng.work_0221.question1;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("程序需要提供命令行参数: 输入文件路径、输出文件路径、旧字符串、新字符串");
            return;
        }

        String inputFilePath = args[0];
        String outputFilePath = args[1];
        String oldStr = args[2];
        String newStr = args[3];

        try {
            argsEnsure(inputFilePath, outputFilePath, oldStr, newStr);
            replaceString(inputFilePath, outputFilePath, oldStr, newStr);
            System.out.println(oldStr + " 成功替换为 " + newStr + " !");
        } catch (Exception e) {
            System.err.println("错误: " + e.getMessage());
        }
    }

    // 命令行异常处理
    private static void argsEnsure(String inputPathFile, String outputPathFile, String oldStr, String newStr) throws FileNotFoundException {
        File inputFile = new File(inputPathFile);
        File outputFile = new File(outputPathFile);

        if (!inputFile.exists()) {
            throw new FileNotFoundException("错误: 输入文件不存在: " + inputPathFile);
        }
        if (inputFile.isDirectory()) {
            throw new IllegalArgumentException("错误: 输入路径是目录: " + inputPathFile);
        }
        if (outputFile.isDirectory()) {
            throw new IllegalArgumentException("错误: 输出路径是目录: " + outputPathFile);
        }
        if (oldStr.isEmpty()) {
            throw new IllegalArgumentException("错误: 旧字符串不能为空");
        }
        if (newStr.isEmpty()) {
            throw new IllegalArgumentException("错误: 新字符串不能为空");
        }
    }

    // 替换字符串
    public static void replaceString(String inputFilePath, String outputFilePath, String oldStr, String newStr) throws IOException {
//        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFilePath));
//             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFilePath))) {
//
//            String oldText;
//
//            while ((oldText = bufferedReader.readLine()) != null) { // 读取文本文件
//                String newText = oldText.replace(oldStr, newStr); // 逐行替换字符串
//                bufferedWriter.write(newText); //修改后的内容保存到新文件
//                bufferedWriter.newLine(); //换行
//            }
//        }

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {

            char[] buffer = new char[8192]; // 8KB 缓冲区
            int charsRead;
            StringBuilder remaining = new StringBuilder();

            // 确保处理大文件的较长行时，内存不会溢出
            while ((charsRead = reader.read(buffer)) != -1) {
                remaining.append(buffer, 0, charsRead);
                int lastNewlineIndex = remaining.lastIndexOf("\n");
                if (lastNewlineIndex != -1) {
                    String completeLines = remaining.substring(0, lastNewlineIndex + 1);
                    writer.write(completeLines.replace(oldStr, newStr));
                    remaining.delete(0, lastNewlineIndex + 1); // 删除已处理的部分
                }
            }

            // 处理剩余的部分
            if (remaining.length() > 0) {
                writer.write(remaining.toString().replace(oldStr, newStr));
            }
        }
    }
}
