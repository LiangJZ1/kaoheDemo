package com.gduf.Data;

public class GetCommand {

    /**获取命令头
     * @param message 完整命令行
     * @return 命令头
     * */
    public static String getCommand(String message)
    {
        int Index = message.indexOf(" ");
        if(Index == -1)
            return message;
        else
            return message.substring(0,Index);
    }


    /**获取命令值，也可以起到去除第一个参数的作用
     * @param message 完整命令行
     * @return 命令值(除第一个参数)，无则返回空白值
    * */
    public static String getCommandMessage(String message)
    {
        if(message == null) return "";
        int Index = message.indexOf(" ");
        if(Index == -1)
        {
            return "";
        }
        else
        {
            return message.substring(Index+1);
        }
    }


    /**获取第一个命令值
     * @param commandValue 命令值
     * @return 第一个命令值,若只有一个值则返回该值，空白返回空字符串
     * */
    public static String getCommandValue(String commandValue) {
        int Index = commandValue.indexOf(" ");
        if(commandValue.equals("-1"))
            return "-1";
        else
            if(Index == -1)
                if(commandValue.isEmpty())
                    return "";
                else
                    return commandValue;
            else
                return commandValue.substring(0,Index);
    }
}
