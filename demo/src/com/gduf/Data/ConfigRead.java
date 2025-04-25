package com.gduf.Data;

import java.io.*;
import java.util.Properties;

public class ConfigRead {
    private final static  String CONFIG_FILE = "Config.properties";//配置文件名默认值
    private final static  String DEFAULT_PORT = "8080";//端口默认值

    /**创建默认文件并创建默认port*/
    public static int configProtRead() {
        CreateFile.createFile();
        Properties props = new Properties();
        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            // 加载配置文件
            props.load(input);
        } catch (IOException e) {
            ErrorLogger.logError(e);
            System.err.println("读取配置文件出错: " + e.getMessage());
        }

        // 2. 检查port是否存在，如果不存在则设置默认值
        if (!props.containsKey("port")) {
            props.setProperty("port", DEFAULT_PORT);
            try (OutputStream output = new FileOutputStream(CONFIG_FILE)) {
                props.store(output, null);
            } catch (IOException e) {
                ErrorLogger.logError(e);
                System.err.println("保存配置文件出错: " + e.getMessage());
            }
            System.out.println("port不存在，已设置为默认值: " + DEFAULT_PORT);
            Logger.logSave("port不存在，已设置为默认值: " + DEFAULT_PORT);
        } else {
            System.out.println("port已存在，当前值: " + props.getProperty("port"));
            Logger.logSave("port已存在，当前值: " + props.getProperty("port"));
        }
        return Integer.parseInt(props.getProperty("port"));
    }

}

