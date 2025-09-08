//基于Ubuntu20.04

package com.xiaomi.wusheng.work_0219.question1;

import java.io.IOException;

public class SedCommand {
    public void executeSedCommand() {
        try {
            String[] sedCommand = {
                    "sed",
                    "-i",
                    "/^#/d; s/ = /=/g",
                    "./src/main/java/com/xiaomi/wusheng/work_0219/question1/config.ini"
            };

            Process process = new ProcessBuilder(sedCommand).start();
            process.waitFor();

            System.out.println("配置文件内容修改完成，并保存回原文件！");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
