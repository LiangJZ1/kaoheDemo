package com.gduf.CommandServer;

import com.gduf.Data.ErrorLogger;
import com.gduf.Data.GetCommand;

import java.util.LinkedList;

import static com.gduf.server.UDPMainServerDemo.hashMapLinked;


public class HashMapLinked {

    public static String lpushCommand(String Commandvalue) {
        String key= GetCommand.getCommandValue(Commandvalue);//取第一个参数key
        String value=GetCommand.getCommandValue(GetCommand.getCommandMessage(Commandvalue));//取第二个参数value
        if(Commandvalue.isEmpty())
            return "用法"+HelpCommand.help("lpush");
        else {
            if (!hashMapLinked.containsKey(key)) {
                hashMapLinked.put(key, new LinkedList<>());
            }
            hashMapLinked.get(key).addFirst(value);
            return "已在["+key+"] 左端添加 ["+value+"]";
        }
    }


    public static String rpushCommand(String Commandvalue) {
        String key= GetCommand.getCommandValue(Commandvalue);//取第一个参数key
        String value=GetCommand.getCommandValue(GetCommand.getCommandMessage(Commandvalue));//取第二个参数value
        if(Commandvalue.isEmpty())
            return "用法"+HelpCommand.help("rpush");
        else {
            if (!hashMapLinked.containsKey(key)) {
                hashMapLinked.put(key, new LinkedList<>());
            }
            hashMapLinked.get(key).addLast(value);
            return "已在["+key+"] 右端添加 ["+value+"]";
        }
    }


    public static String rangeCommand(String Commandvalue) {
        String key= GetCommand.getCommandValue(Commandvalue);//取第一个参数key
        String stringStart=GetCommand.getCommandValue(GetCommand.getCommandMessage(Commandvalue));//取第二个参数start
        String stringEnd=GetCommand.getCommandValue(GetCommand.getCommandMessage(GetCommand.getCommandMessage(Commandvalue)));//取第三个参数end
        int start=-1;
        int end=-1;
        try {
            // 1. 检查数据合法
            if (stringStart.isEmpty()) {
                return "缺少start参数";
            }
            start = Integer.parseInt(stringStart);
        } catch (NumberFormatException e) {
            // 3. 统一处理错误（包括非数字、超范围等情况）
            System.err.println("非法参数" + e.getMessage());          // 打印到标准错误
            ErrorLogger.logError(e);               // 记录错误日志
        }
        try {
            // 1. 检查数据合法
            if (stringEnd.isEmpty()) {
                return "缺少end参数";
            }
            end = Integer.parseInt(stringEnd);
        } catch (NumberFormatException e) {
            // 3. 统一处理错误（包括非数字、超范围等情况）
            System.err.println("非法参数" + e.getMessage());          // 打印到标准错误
            ErrorLogger.logError(e);               // 记录错误日志
        }

        if (!hashMapLinked.containsKey(key)) {
            return "[]";  // 返回空列表的字符串表示
        }

        LinkedList<String> list = hashMapLinked.get(key);
        int size = list.size();//获取链表长度
        // 处理负数索引和超出范围的情况
        if (start < 0 || start > size) return "start参数不能小于0或者大于链表长度";
        if (end >= size || end < 0) return "end参数不能大于链表长度-1或者小于0";
        if (start > end) return "start不能大于end";

        // 构建结果字符串
        String result = "[";
        for (int i = start; i <= end; i++) {
            if (i > start) {
                result = result + ", ";
            }
            result = result + list.get(i);
        }
        result = result + "]";
        return result;
    }


    public static String lenCommand(String Commandvalue) {
        String key= GetCommand.getCommandValue(Commandvalue);//取参数key
        if(Commandvalue.isEmpty())
            return "用法"+HelpCommand.help("lpush");
        else {
            if (!hashMapLinked.containsKey(key)) {
                return "key["+key+"]不存在";
            }
            return "链表["+key+"]长度为"+hashMapLinked.get(key).size();
        }
    }

    public static String lpopCommand(String Commandvalue) {
        String key= GetCommand.getCommandValue(Commandvalue);//取参数key
        if(Commandvalue.isEmpty())
            return "用法"+HelpCommand.help("lpush");
        else {
            if (!hashMapLinked.containsKey(key)) {
                return "不存在的key["+key+"]";
            }
            else
                return "已删除 ["+key+"] 左端 ["+hashMapLinked.get(key).removeFirst()+"]";
        }
    }

    public static String rpopCommand(String Commandvalue){
        String key= GetCommand.getCommandValue(Commandvalue);//取参数key
        if(Commandvalue.isEmpty())
            return "用法"+HelpCommand.help("lpush");
        else {
            if (!hashMapLinked.containsKey(key)) {
                return "不存在的key["+key+"]";
            }
            else
                return "已删除 ["+key+"] 右端 ["+hashMapLinked.get(key).removeLast()+"]";
        }
    }

    public static String ldelCommand(String Commandvalue) {
        String key= GetCommand.getCommandValue(Commandvalue);//取参数key
        hashMapLinked.remove(key);
        return "已经删除键值对["+key+"]";
    }
}
