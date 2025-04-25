package com.gduf.Data;

import java.io.File;
import java.io.IOException;
import java.util.Properties;


public class CreateFile {
    /**
     * 创建文件
     */
    public static void createFile() {
        File ConfigFile = new java.io.File("Config.properties");
        File DataFile = new java.io.File("Log.txt");
        File ErrorFile = new java.io.File("Error.txt");
        if (!ConfigFile.exists())
            try {
                ConfigFile.createNewFile();
                System.out.println("Config.properties文件创建成功");
                Logger.logSave("Config.properties文件创建成功");
            } catch (IOException e) {
                e.printStackTrace();
            }
        if (!DataFile.exists())
            try {
                DataFile.createNewFile();
                System.out.println("Log.txt文件创建成功");
                Logger.logSave("Log.txt文件创建成功");
            } catch (IOException e) {
                e.printStackTrace();
            }
        if (!ErrorFile.exists())
            try {
                ErrorFile.createNewFile();
                System.out.println("Error.txt文件创建成功");
                Logger.logSave("Error.txt文件创建成功");
            } catch (IOException e) {
            }
    }

    /**
    读取配置文件
     */
    public static void ReadConfigFile(){
        Properties props = new Properties();

    }

    }



