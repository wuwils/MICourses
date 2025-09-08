package com.xiaomi.wusheng.work_0223.question_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientHandler implements Runnable{
    private Socket sock;

    public ClientHandler(Socket sock){
        this.sock = sock;
    }

    @Override
    public void run(){
        try (InputStream input = sock.getInputStream()){
            BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
            while(true){
                String message = reader.readLine();
                if (message == null){
                    break;
                }
                System.out.println("<<< " + message); // 客户端打印出服务器的响应
            }
        }catch(IOException e){
            System.out.println("Server connection closed.");
        }finally{
            try {
                sock.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}