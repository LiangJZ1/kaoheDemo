package com.gduf.Data;

import java.io.*;
import java.util.Properties;

import static com.gduf.server.UDPMainServerDemo.hashMap;

public class HashMapFile {
    // HashMap文件路径
    public static final String HASH_MAP_FILE_PATH ="HashMap.properties";

    /**保存HashMap到磁盘*/
    public static void saveHashMap() {
        try (FileWriter writer = new FileWriter(HASH_MAP_FILE_PATH)) {
            for (String key : hashMap.keySet()) {
                // 写入键值对，格式为：key=value
                writer.write(key + "=" + hashMap.get(key));
                writer.write("\n"); // 换行
            }
            System.out.println("HashMap数据已成功保存到文件: " + HASH_MAP_FILE_PATH);
            Logger.logSave("HashMap数据已成功保存到文件: " + HASH_MAP_FILE_PATH);
        } catch (IOException e) {
            ErrorLogger.logError(e);
            System.err.println("保存HashMap到文件时出错: " + e.getMessage());
        }
    }

    /** 从磁盘文件读取HashMap*/
    public static void readHashMap() {
        File file = new File(HASH_MAP_FILE_PATH);
        if (!file.exists()) {
            System.out.println("文件不存在，使用save命令保存并创建HashMap.properties文件");
        } else {
            Properties props = new Properties();
            try (FileInputStream in = new FileInputStream(HASH_MAP_FILE_PATH)) {
                // 加载文件内容
                props.load(in);
                // 写入HashMap
                for (String key : props.stringPropertyNames()) {
                    hashMap.put(key, props.getProperty(key));
                }
                System.out.println("从文件成功读取HashMap数据");
                Logger.logSave("从文件成功读取HashMap数据");
            } catch (IOException e) {
                ErrorLogger.logError(e);
                System.err.println("从文件读取HashMap时出错: " + e.getMessage());
            }
        }
    }
}
