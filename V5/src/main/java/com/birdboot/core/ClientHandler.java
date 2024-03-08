package com.birdboot.core;

import com.birdboot.http.HttpServletRequest;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.URISyntaxException;
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
        try {
            //1 解析请求(目的：读取浏览器发送的请求内容以请求对象形式保存请求所有内容)
            //  请求对象的构造器中会读到浏览器发送的请求内容并保存到自身属性上
            HttpServletRequest request = new HttpServletRequest(socket);

            //2 处理请求




            //3 发送响应
            /*
            *   测试目标：将static目录下的index.html页面发送给浏览器进行展现
            *   掌握的知识：  1：HTTP协议中响应的格式
            *               2：在java中常用的相对路径（定位static目录）
            *               new File("./V5/src/main/resources/static/index.html") 相对路径弃用
            *               类加载路径
            *               类加载路径在java中定位：
            *               File dir = new File(
            *                   当前类的类名.class.getClassLoader().getResource(".").toURI
            *               )
            *               类加载路径定位的目录在哪里
            *               当前类所在包的最上层报的上一级
            *               举例：
            *               Clienthandler类的包：com.birdboot.core
            *               这说明Clienthandler类在core包里，core包在birdboot包里
            *               这里类加载路径就是com的上一级
            *               实际上虚拟机执行的是.class文件，因此上述的类加载路径对应的应当是：
            *               Clienthandler.class文件所在的包（core目录）.com在birdboot中，birdboot在com中
            *               类加载路径就是这个com的上一级（这里的target/classes目录）
            *
            *               定位到类加载路径的好处：
            *               通过该目录可以找到项目中所有类，resources下的资源也会在这个目录下
            *
            *
            * */

            // 使用类加载路径定位到当前项目下的static目录
            // 1：类加载路径（实际定位的是V5项目中target/classes目录）
            File baseDir = new File(
                    ClientHandler.class.getClassLoader().getResource(".").toURI()
            );
            // 2:定位static目录
            File staticDir = new File(baseDir,"static");













            String method = request.getMethod();
            System.out.println("请求方式"+ method);

            String url = request.getUrl();
            System.out.println("抽象路径"+ url);

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

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
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
