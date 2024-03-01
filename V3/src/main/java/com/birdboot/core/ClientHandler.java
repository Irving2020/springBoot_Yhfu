package com.birdboot.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

//            String line = readLine();
//            System.out.println(line);
//            String[] arr = line.split(" ");
//            String method = arr[0];
//            String url = arr[1];
//            String protocol = arr[2];
//
//            System.out.println("method"+method);
//            System.out.println("url"+url);
//            System.out.println("protocol"+protocol);

//            Map<String, Integer> map = new HashMap<>();
//            map.put("数学",20);
//            map.put("英语",60);
//            map.put("语文",80);
//            Integer in = map.put("英语",80);
//            System.out.println("in" + in);
//            System.out.println("map" + map);

        // todo: 写一个计算各字符在指定字符串出现的次数的方法 将结果存放到map中并作为结果返回
        String lineStr = "helloworld!!thinking in java!!!我要成为JAVA高手！";
        Map<Character, Integer> resultMap = new HashMap<>();
        for (int i = 0;i < lineStr.length(); i++){
            char word = lineStr.charAt(i);
            if(resultMap.containsKey(word)){
                int num = resultMap.get(word);
                num++;
                resultMap.put(word,num);
            } else {
                resultMap.put(word,1);
            }
        }
        /*
         *  keySet 返回一个set集合 取所有key
         * */
        Set<Character> resultMapKeys = resultMap.keySet();
        /*
        *   enterySet 返回一个set集合
        * */
//            Set<HashMap.Entry<Character, Integer>> entries = resultMap.entrySet();
//            for (HashMap.Entry<Character, Integer> e: entries){
//                System.out.println(e.getKey() + ":" + e.getValue());
//            }
        /*
         *   遍历所有value,Collecitons values() 将当前map中所有的value以一个集合形式返回 取所有value java8中支持lamba表达式 forEach
         * */
        Collection<Integer> values = resultMap.values();
        System.out.println("values Collections" + values);

//            System.out.print(entries);

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
}
