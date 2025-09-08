package com. xiaomi. wusheng. course_0221.IOTest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileIOTest {

    public static void main(String[] args) {
        // 1. 在当前目录下创建一个名为"IOTest"的文件夹
        File dir = new File("./src/main/java/com/xiaomi/wusheng/course_0221/IOTest");
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                System.out.println("目录 IOTest 创建成功");
            } else {
                System.out.println("目录 IOTest 创建失败");
                return;
            }
        } else {
            System.out.println("目录 IOTest 已存在");
        }

        // 2. 在IOTest文件夹中创建一个名为"example.txt"的文件
        File file = new File(dir, "example.txt");
        try {
            if (file.createNewFile()) {
                System.out.println("文件 example.txt 创建成功");
            } else {
                System.out.println("文件 example.txt 已存在");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // 3. 向"example.txt"写入一些文本内容
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("这是一个示例文本内容。\nHello, World!");
            System.out.println("内容已写入 example.txt");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // 4. 读取并打印"example.txt"的内容
        try {
            List<String> lines = Files.readAllLines(Paths.get(file.getPath()));
            System.out.println("example.txt 的内容如下：");
            for (String line : lines) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // 5. 列出"IOTest"文件夹中的所有文件
        File[] files = dir.listFiles();
        if (files != null) {
            System.out.println("IOTest 目录中的文件列表：");
            for (File f : files) {
                System.out.println(f.getName());
            }
        } else {
            System.out.println("IOTest 目录为空或无法访问");
        }
    }
}