package com.xiaomi.wusheng.work_0223.question_2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ServerHandler extends Thread{
    Socket sock;

    public ServerHandler(Socket sock){
        this.sock = sock;
    }

    @Override
    public void run(){
        try (InputStream input = this.sock.getInputStream();
             OutputStream output = this.sock.getOutputStream()){
            BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8));

            writer.write("Connect to server!\n"); //服务器响应，连接成功
            writer.flush();

            while(true){
                String message = reader.readLine();
                if (message == null || message.equals("bye")) { // 接收到bye表明客户端断开连接
                    break;
                }
                System.out.println("Received from client: " + message); //服务器打印出客户端发来的消息

                writer.write("Server: " + message + "\n"); //服务器响应消息内容
                writer.flush();
            }
        }catch(IOException e){
            System.out.println("Client disconnected.");
        }finally{
            try{
                this.sock.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}