package com.gduf.CommandServer;

import com.gduf.Data.GetCommand;
import com.gduf.Data.HashMapFile;

import static com.gduf.server.UDPMainServerDemo.hashMap;

/**Hashmap类命令实现
 */
public class HashMapCommand {

    /**
     * set命令实现
     * @param Commandvalue 命令值 */
    public static String setCommand(String Commandvalue) {
        String key=GetCommand.getCommandValue(Commandvalue);
        String value=GetCommand.getCommandValue(GetCommand.getCommandMessage(Commandvalue));//取第二个参数value
        if(Commandvalue.isEmpty())
            return "用法"+HelpCommand.help("set");
        else {
            hashMap.put(key,value);
            System.out.println(hashMap);//测试用
            HashMapFile.saveHashMap();
            return "已设置 key["+key+"] value ["+value+"]";
        }
    }


    public static String getCommand(String Commandvalue) {
        String key=GetCommand.getCommandValue(Commandvalue);
        if(Commandvalue.isEmpty())
            return "用法"+HelpCommand.help("get");
        else
            return "["+key+"]"+"的值为["+ hashMap.get(key)+"]";
    }

    public static String delCommand(String Commandvalue) {
        String key=GetCommand.getCommandValue(Commandvalue);
        if(Commandvalue.isEmpty())
            return "用法"+HelpCommand.help("del");
        else {
            hashMap.put(key,"");
            HashMapFile.saveHashMap();
            System.out.println(hashMap);//测试用
            return "已删除 key["+key+"]的值";
        }
    }


    public static String hdelCommand(String Commandvalue) {
        String key=GetCommand.getCommandValue(Commandvalue);
        if(Commandvalue.isEmpty())
            return "用法"+HelpCommand.help("del");
        else {
            hashMap.remove(key);
            HashMapFile.saveHashMap();
            System.out.println(hashMap);//测试用
            return "已删除键值对["+key+"]";
        }
    }
}
