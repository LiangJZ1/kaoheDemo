package com.gduf.server;

import com.gduf.CommandServer.HelpCommand;
import com.gduf.CommandServer.HashMapCommand;
import com.gduf.CommandServer.HashMapLinked;
import com.gduf.Data.AllHelp;
import com.gduf.Data.GetCommand;


public class Command {
    private String message;
    private String command;
    public String CommandMain(String message) {
        command = GetCommand.getCommand(message);
        System.out.println("命令行："+message);
        switch (command) {
            case "ping":
                return "pong";
            case "help":
                if(GetCommand.getCommandMessage(message).isEmpty())
                    return AllHelp.getAllHelp();
                else
                    return HelpCommand.help(GetCommand.getCommandMessage(message));
            case "set":
                return HashMapCommand.setCommand(GetCommand.getCommandMessage(message));
            case "get":
                return HashMapCommand.getCommand(GetCommand.getCommandMessage(message));
            case "del":
                return HashMapCommand.delCommand(GetCommand.getCommandMessage(message));
            case "hdel":
                return HashMapCommand.hdelCommand(GetCommand.getCommandMessage(message));
            case "lpush":
                return HashMapLinked.lpushCommand(GetCommand.getCommandMessage(message));
            case "rpush":
                return HashMapLinked.rpushCommand(GetCommand.getCommandMessage(message));
            case "range":
                return HashMapLinked.rangeCommand(GetCommand.getCommandMessage(message));
            case "len":
                return HashMapLinked.lenCommand(GetCommand.getCommandMessage(message));
            case "lpop":
                return HashMapLinked.lpopCommand(GetCommand.getCommandMessage(message));
            case "rpop":
                return HashMapLinked.rpopCommand(GetCommand.getCommandMessage(message));
            case "ldel":
                return HashMapLinked.ldelCommand(GetCommand.getCommandMessage(message));
            default:
                return "[CommandMain]未知指令";
        }

    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Command(String message) {
        this.message = message;
    }
}
