package com.im.message.command;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleCommandManager implements ConsoleCommand {
    private Map<String,ConsoleCommand> consoleCommandMap;

    public ConsoleCommandManager() {
        this.consoleCommandMap = new HashMap<>();
        consoleCommandMap.put("sendToUser",new SendToUserConsoleCommand());
        consoleCommandMap.put("logout",new LogoutConsoleCommand());
        consoleCommandMap.put("createGroup",new CreateGroupConsoleCommand());
    }

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("请输入要执行的指令：【sendToUser，createGroup】");
        String command = scanner.nextLine().trim();
        if("".equals(command)){
            System.out.println("返回，收到的command是:"+command);
            return;
        }
        ConsoleCommand consoleCommand = consoleCommandMap.get(command);

        if(consoleCommand != null){
            System.out.println("收到的command是【"+command+"】，执行ConsoleCommand类是：【"+consoleCommand.getClass()+"】");
            consoleCommand.exec(scanner,channel);
        }else {
            System.out.println("收到的command是【"+command+"】，无法识别指令【"+command+"】,请重新输入");
        }
    }
}
