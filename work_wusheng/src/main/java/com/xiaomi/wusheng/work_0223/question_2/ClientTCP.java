/**
 * 命令行输入：
 * mvn exec:java -Dexec.mainClass="com.xiaomi.wusheng.work_0223.question_2.ClientTCP"
 *
 * 结果保存在 src/main/resources/work_0223/question2
 * **/

package com.xiaomi.wusheng.work_0223.question_2;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ClientTCP{
    public static void main(String[] args) throws IOException{
        Socket sock = new Socket("localhost", 6666);
        System.out.println("Connected to server.");

        Thread receiveThread = new Thread(new ClientHandler(sock));
        receiveThread.start();

        try (OutputStream output = sock.getOutputStream()){
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8));
            Scanner scanner = new Scanner(System.in);
            while(true){
                String message = scanner.nextLine();
                writer.write(message);
                writer.newLine();
                writer.flush(); // 客户端输入消息，并发送给服务器
                if (message.equals("bye")){ //客户端输入bye则断开连接
                    break;
                }
            }
        }

        sock.close();
        System.out.println("Disconnected.");
    }
}