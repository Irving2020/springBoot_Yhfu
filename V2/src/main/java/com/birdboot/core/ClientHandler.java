package com.birdboot.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/*
*   该线程负责与指定客户端进行http交互
*
* */
public class ClientHandler implements Runnable {
    private Socket socket;
    public ClientHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream in = socket.getInputStream();
            int d;
            char pre = 'a', cur = 'a';
            StringBuilder builder = new StringBuilder();
            // pre记录上次读取的字符 cur记录本次读取的字符
            while((d = in.read()) != -1){
                cur = (char)d;
                if(pre == 13 && cur == 10){
                    break;
                }
                builder.append(cur);
                pre = cur;
            }
            String line = builder.toString().trim();
            String[] arr = line.split(" ");
            String method = arr[0];
            String url = arr[1];
            String protocol = arr[2];

            System.out.println("method"+method);
            System.out.println("url"+url);
            System.out.println("protocol"+protocol);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
