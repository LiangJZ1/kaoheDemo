package com.gduf.CommandServer;

public class HelpCommand {
    public static String help(String message)
    {
        switch (message) {
            case "set":
                return "set [key] [value]";
            case "get":
                return "get [key]";
            case "del":
                return "del [key]";
            case  "hdel":
                return "hdel [key]";
            case "lpush":
                return "lpush [key] [value]";
            case "rpush":
                return "rpush [key] [value]";
            case "range":
                return "range [key] [start] [end]";
            case "len":
                return "len [key]";
            case "lpop":
                return "lpop [key]";
            case "rpop":
                return "rpop [key]";
            case "ldel":
                return "ldel [key]";
            case "ping":
                return "服务端响应 pong";
            default:
                return "查无指令，使用help查看全部指令";
        }
    }
}
