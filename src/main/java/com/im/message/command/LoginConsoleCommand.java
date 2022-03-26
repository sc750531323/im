package com.im.message.command;

import com.im.message.packet.LoginRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;
import java.util.UUID;

public class LoginConsoleCommand implements ConsoleCommand{
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("start register to server...");
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setName("flash");
        loginRequestPacket.setPassword("pwd");
        channel.writeAndFlush(loginRequestPacket);
    }
}
