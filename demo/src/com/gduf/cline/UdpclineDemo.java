package com.gduf.cline;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpclineDemo {
    /*
    * UDP客户端
     */
    public static void main(String[] args) throws IOException {
        System.out.println("====客户端启动====");
        byte[] buf = new byte[1024];
        DatagramPacket packet2 = new DatagramPacket(buf, buf.length);
        //向服务端（127.0.1）发送用户输入的字符串
        while(true){
            System.out.println("请输入要发送的指令：");
            //限制输入字符串长度1024字节以内
            String str = new java.util.Scanner(System.in).nextLine();
            if(str.length()>50){
                System.out.println("输入的字符串长度不能超过1024字节");
                continue;
            }

            byte[] data = str.getBytes();
            //打包数据，发送到127.0.1ip服务端，端口8080
            DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getByName("127.0.0.1"), 8080);
            DatagramSocket socket = new DatagramSocket();//随机分配端口
            socket.send(packet);
            socket.receive(packet2);
            int len=packet2.getLength();
            System.out.println(packet2.getAddress().getHostAddress()+">  "+new String(buf,0,len));
            socket.close();
        }

    }
}
