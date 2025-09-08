/**
 * 命令行输入：
 * mvn exec:java -Dexec.mainClass="com.xiaomi.wusheng.work_0223.question_3.ClientNIO"
 *
 * 结果保存在 src/main/resources/work_0223/question3
 * **/

package com.xiaomi.wusheng.work_0223.question_3;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientNIO{
    public static void main(String[] args){
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 1; i <= 10; i++) {
            int client = i;
            executorService.submit(() -> {
                try (SocketChannel channel = SocketChannel.open()){
                    channel.connect(new InetSocketAddress("localhost", 8888));
                    System.out.println("Client " + client + " has connected to Server.");

                    String message = "This is Client " + client;
                    ByteBuffer buffer1 = ByteBuffer.wrap(message.getBytes());
                    channel.write(buffer1);

                    ByteBuffer buffer2 = ByteBuffer.allocate(1024);
                    channel.read(buffer2);
                    buffer2.flip();
                    String response = new String(buffer2.array()).trim();
                    System.out.println("Client " + client + " has received from Server: " + response);
                }catch (IOException e){
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
    }
}
