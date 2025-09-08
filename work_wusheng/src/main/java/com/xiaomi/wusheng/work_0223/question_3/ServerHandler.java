package com.xiaomi.wusheng.work_0223.question_3;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerHandler{
    public void handleAccept(SelectionKey key, Selector selector) throws IOException{
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel channel = serverSocketChannel.accept();
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ);
        System.out.println("Connected from " + channel.getRemoteAddress());
    }

    public void handleRead(SelectionKey key) throws IOException{
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int bytesRead = channel.read(buffer);
        if (bytesRead == -1) {
            System.out.println("Client disconnected: " + channel.getRemoteAddress());
            channel.close();
            return;
        }

        buffer.flip();
        byte[] data = new byte[buffer.limit()];
        buffer.get(data);
        String message = new String(data).trim();
        System.out.println("Client: " + message);

        String response = "Server: " + message;
        channel.write(ByteBuffer.wrap(response.getBytes()));
    }
}