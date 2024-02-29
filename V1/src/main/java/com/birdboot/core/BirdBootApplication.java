package com.birdboot.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

/*
 * 主启动类
 */
public class BirdBootApplication {
    /*
    * serverSocket 用于与客户端建立tcp链接
    * */
    private ServerSocket serverSocket;

    /*
    *   构造器用于初始化服务端程序
    */
    public BirdBootApplication(){
        try {
            System.out.println("正在启动服务器...");
            serverSocket = new ServerSocket(8088);
            System.out.println("服务器启动完毕！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
    *   服务端开始工作
    * */
    public void start(){
        try {
            System.out.println("等待客户端连接...");
            Socket socket = serverSocket.accept();

            ClientHandler handler = new ClientHandler(socket);
            Thread t = new Thread(handler);
            t.start();

            System.out.println("一个客户端连接了！");
            System.out.println(serverSocket.getLocalSocketAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BirdBootApplication application = new BirdBootApplication();
        application.start();
        System.out.println('2');
    }
}
