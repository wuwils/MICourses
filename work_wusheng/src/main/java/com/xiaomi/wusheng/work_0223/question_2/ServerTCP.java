/**
 * 命令行输入：
 * mvn exec:java -Dexec.mainClass="com.xiaomi.wusheng.work_0223.question_2.ServerTCP"
 *
 * 结果保存在 src/main/resources/work_0223/question2
 * **/

package com.xiaomi.wusheng.work_0223.question_2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCP{
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(6666);
        System.out.println("Server is running...");
        while(true){
            Socket sock = ss.accept();
            System.out.println("Connected from " + sock.getRemoteSocketAddress());
            Thread t = new ServerHandler(sock);
            t.start();
        }
    }
}