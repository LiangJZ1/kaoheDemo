package com.gduf.server;

import com.gduf.Data.ErrorLogger;
import com.gduf.Data.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static com.gduf.server.UDPMainServerDemo.clientMap;

/**
 * 客户端处理线程类
 * 每个客户端连接对应一个独立的处理线程
 */
public class ClientHandler {
    // 客户端的IP地址
    private final InetAddress clientIP;
    // 客户端的端口号
    private final int clientPort;

    /**
     * 使用BlockingQueue实现主子线程消息传递
     */
    private final BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();

    // 控制处理线程运行的标志
    private volatile boolean running = true;

    // 每个客户端线程对应的socket，用于发送响应
    private DatagramSocket clientSocket;

    /**
     * 创建客户端线程
     * 构造函数
     * @param clientIP 客户端的IP地址
     * @param clientPort 客户端的端口号
     */
    public ClientHandler(InetAddress clientIP, int clientPort) {
        this.clientIP = clientIP;
        this.clientPort = clientPort;

        try {
            // 为每个客户端线程创建独立的socket
            // 使用无参构造函数会绑定到随机可用端口
            this.clientSocket = new DatagramSocket();

            System.out.println("已为客户端 " + clientIP.getHostAddress() + ":" + clientPort + " 创建专用socket，端口: " + clientSocket.getLocalPort());
            Logger.logSave("已为客户端 " + clientIP.getHostAddress() + ":" + clientPort + " 创建专用socket，端口: " + clientSocket.getLocalPort());
        } catch (SocketException e) {
            ErrorLogger.logError(e);
            System.err.println("创建客户端"+Thread.currentThread().getName()+"socket失败: " + e.getMessage());
            return;
        }

        // 创建并启动处理线程
        Thread processorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 调用消息处理方法
                processMessages();
            }
        });

        // 设置线程名称
        processorThread.setName("ClientHandler-" + clientIP.getHostAddress() + ":" + clientPort);

        // 启动线程
        processorThread.start();

        System.out.println("已启动客户端"+clientIP.getHostAddress() + ":" + clientPort+"处理线程");
        Logger.logSave("已启动客户端"+clientIP.getHostAddress() + ":" + clientPort+"处理线程");
    }

    /**
     * 添加消息到队列（由主线程调用）
     * @param message 要添加的消息
     */
    public void addMessage(String message) {
        // 将消息放入队列（非阻塞方法）
        // offer()方法在队列满时会立即返回false，而put()会阻塞
        boolean success = messageQueue.offer(message);

        if (!success) {
            Logger.logSave("警告: 无法将消息添加到队列，可能队列已满");
            System.err.println("警告: 无法将消息添加到队列，可能队列已满");
        }
    }

    /**
     * 处理消息的方法（运行在独立的线程中）
     */
    private void processMessages() {
        try {
            System.out.println("客户端"+Thread.currentThread().getName()+"处理线程开始运行");
            Logger.logSave("客户端"+Thread.currentThread().getName()+"处理线程开始运行");

            // 线程主循环
            while (running) {
                //阻塞从队列中获取消息
                String message = messageQueue.take();

                //传递接收到的消息
                System.out.println("客户端[" + Thread.currentThread().getName() + "] 指令: " + message);
                Logger.logSave("客户端[" + Thread.currentThread().getName() + "] 指令: " + message);
                Command command = new Command(message);
                String commandReturn = command.CommandMain(message);

                // 发送数据
                byte[] sendData = commandReturn.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientIP, clientPort);

                // 发送响应给客户端
                clientSocket.send(sendPacket);
                System.out.println("服务端回应[" + Thread.currentThread().getName() + "] 处理结果: " + commandReturn);
                Logger.logSave("服务端回应[" + Thread.currentThread().getName() + "] 处理结果: " + commandReturn);
            }
        } catch (InterruptedException e) {
            // 线程被中断时的处理
            ErrorLogger.logError(e);
            System.out.println("处理线程被中断: " + Thread.currentThread().getName());
            Logger.logSave("处理线程被中断: " + Thread.currentThread().getName());
        } catch (IOException e) {
            // IO异常处理
            ErrorLogger.logError(e);
            System.err.println("发送响应时发生IO异常: " + e.getMessage());
        } finally {
            System.out.println("开始清理客户端"+Thread.currentThread().getName()+"处理线程资源");
            Logger.logSave("开始清理客户端"+Thread.currentThread().getName()+"处理线程资源");

            // 确保从映射表中移除当前客户端
            String clientKey = clientIP.getHostAddress() + ":" + clientPort;
            clientMap.remove(clientKey);

            // 关闭socket
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
                System.out.println("客户端"+Thread.currentThread().getName()+"socket已关闭");
                Logger.logSave("客户端"+Thread.currentThread().getName()+"socket已关闭");
            }

            System.out.println("客户端"+Thread.currentThread().getName()+"处理线程资源清理完成: " + clientKey);
            Logger.logSave("客户端"+Thread.currentThread().getName()+"处理线程资源清理完成: " + clientKey);
        }
    }
}
