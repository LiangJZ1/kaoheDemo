package com.gduf.server;

import com.gduf.Data.ConfigRead;
import com.gduf.Data.ErrorLogger;
import com.gduf.Data.HashMapFile;
import com.gduf.Data.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.LinkedList;

public class UDPMainServerDemo {
    // UDP数据包的最大大小（字节）
    private static final int MAX_PACKET_SIZE = 1024;
    // 服务器的端口号
    public static final int SERVER_PORT=ConfigRead.configProtRead();
    /**
     * 存储所有客户端处理线程的映射表
     * Key: 字符串格式为"IP地址:端口号"
     * Value: 对应的客户端处理线程对象
     */
    protected static HashMap<String, ClientHandler> clientMap = new HashMap<>();

    //创建HashMap
    public static HashMap<String,String> hashMap =new HashMap<>();

    //创建HashMap,用于存储Linked
    public static HashMap<String, LinkedList<String>> hashMapLinked=new HashMap<>();


    /** 服务器的主socket，用于接收所有客户端消息*/
    private static DatagramSocket serverSocket;

    public static void main(String[] args) {
        //读取HashMap
        HashMapFile.readHashMap();
        try {
            // 创建UDP socket并绑定到指定端口
            serverSocket = new DatagramSocket(SERVER_PORT);
            System.out.println("UDP服务器已启动，正在监听端口: " + SERVER_PORT);
            Logger.logSave("UDP服务器已启动，正在监听端口: " + SERVER_PORT);
            // 主线程的接收循环
            while (true) {

                // 接收数据
                byte[] receiveData = new byte[MAX_PACKET_SIZE];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                // 获取客户端的IP地址
                InetAddress clientIP = receivePacket.getAddress();
                // 获取客户端的端口号
                int clientPort = receivePacket.getPort();
                // 创建客户端的唯一标识（IP:端口）
                String clientKey = clientIP.getHostAddress() + ":" + clientPort;

                // 获取该客户端的处理线程
                ClientHandler handler = clientMap.get(clientKey);
                // 如果该客户端还没有处理线程，则创建一个新的
                if (handler == null) {
                    System.out.println("检测到新客户端连接: " + clientKey);
                    Logger.logSave("检测到新客户端连接: " + clientKey);

                    // 创建新的客户端处理线程
                    handler = new ClientHandler(clientIP, clientPort);

                    // 将新线程放入hashmap表
                    clientMap.put(clientKey, handler);

                    System.out.println("已为新客户端"+clientKey+"创建处理线程");
                    Logger.logSave("已为新客户端"+clientKey+"创建处理线程");
                }
                //数据转字符串
                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                // 将消息交给对应的客户端处理线程
                handler.addMessage(message);
            }
        } catch (IOException e) {
            // 处理IO异常
            System.err.println("服务器发生IO异常: " + e.getMessage());
            ErrorLogger.logError(e);
        } finally {
            // 确保服务器socket被关闭
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
                System.out.println("服务器socket已关闭");
                Logger.logSave("服务器socket已关闭");
            }
        }
    }
}
