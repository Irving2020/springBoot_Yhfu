package com.birdboot.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
*   该类的每一个实例用于表示浏览器发送过来的一个请求
*   HTTP协议要求一个请求有三个部分组成
*   1：请求行
*   2：请求头
*   3：消息正文
* */
public class HttpServletRequest {
    private Socket socket;
    private String method;      // 请求方式
    private String url;         // 抽象路径
    private String protocol;    // 协议版本
    private Map<String,String> headers = new HashMap<>();   // key：消息头的名字 value：消息头对应的值
    public  HttpServletRequest(Socket socket) throws IOException {
        this.socket = socket;
    }

    // 解析请求行
    public void parseRerquestLine() throws IOException {
        String line = readLine();
        System.out.println(line);
        String[] arr = line.split(" ");
        method = arr[0]; // 请求方式
        url = arr[1];    // 抽象路径
        protocol = arr[2];   // 协议版本

        System.out.println("method"+method);
        System.out.println("url"+url);
        System.out.println("protocol"+protocol);
    }
    // 解析请求头
    public void parseHeaders() throws IOException {
        while(true){
            String line = readLine();
            if(line.isEmpty()){
                break;
            }
            System.out.println("消息头" + line);
            // 将每个消息头按照“： ”拆分 ,将消息头的名字作为key,值作为value保存到headers中，
            String[] data = line.split(":\\s");
            headers.put(data[0],data[1]);
        }
    }
    // 解析消息正文
    public void parseContent(){

    }
    private String readLine() throws IOException {
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

        return builder.toString().trim();
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getProtocol() {
        return protocol;
    }

    /*
    *   根据消息头的名字获取对象消息头的值
    * */
    public String getHeader(String name) {
        /*
           只需将name作为key传入查出对应的value返作为返回值
        */
        return headers.get(name);
    }
}
