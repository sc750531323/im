package com.im.message.command;

import com.im.message.packet.LoginRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;
import java.util.UUID;

public class LoginConsoleCommand implements ConsoleCommand{
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("输入您的用户名：");
        String userName = scanner.next();
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setName(userName);
        loginRequestPacket.setPassword("pwd");
        System.out.println("start register to server,user info:["+loginRequestPacket+"]");
        channel.writeAndFlush(loginRequestPacket);
    }
}
