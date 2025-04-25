package com.gduf.Data;

import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    // 日志文件名
    private static final String LOG_FILE = "Log.txt";

    // 保存日志的静态方法
    public static void logSave(String message) {
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            writer.write(message + "\n"); // 写入消息并换行
        } catch (IOException e) {
            ErrorLogger.logError(e);
            e.printStackTrace();
        }
    }
}
