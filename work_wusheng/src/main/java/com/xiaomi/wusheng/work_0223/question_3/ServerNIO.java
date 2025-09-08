/**
 * 命令行输入：
 * mvn exec:java -Dexec.mainClass="com.xiaomi.wusheng.work_0223.question_3.ServerNIO"
 *
 * 结果保存在 src/main/resources/work_0223/question3
 * **/

package com.xiaomi.wusheng.work_0223.question_3;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ServerNIO{
    public static void main(String[] args){
        try (Selector selector = Selector.open(); ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()){
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(8888));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Server is running...");

            ServerHandler serverHandler = new ServerHandler();
            while(true){
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectedKeys.iterator();
                while (iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    if (key.isAcceptable()){
                        serverHandler.handleAccept(key, selector);
                    } else if (key.isReadable()){
                        serverHandler.handleRead(key);
                    }
                    iterator.remove();
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}