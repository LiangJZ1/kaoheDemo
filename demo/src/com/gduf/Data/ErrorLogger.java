package com.gduf.Data;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ErrorLogger {

    // 记录异常到文件的方法
    public static void logError(Exception e) {
        try {
            // 创建文件写入器，追加模式
            FileWriter fileWriter = new FileWriter("Error.txt", true);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            // 获取当前时间
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = dateFormat.format(new Date());

            // 写入异常信息
            printWriter.println("[" + currentTime + "] 发生异常:");
            e.printStackTrace(printWriter); // 将异常堆栈信息写入文件
            printWriter.println(); // 添加空行分隔

            // 关闭资源
            printWriter.close();
            fileWriter.close();

        } catch (IOException ioException) {
            // 如果写文件本身出错，至少打印到控制台
            System.err.println("无法写入错误日志文件:");
            ioException.printStackTrace();
        }
    }
}
