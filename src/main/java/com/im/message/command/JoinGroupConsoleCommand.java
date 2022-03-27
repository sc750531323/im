package com.im.message.command;

import com.im.message.packet.JoinGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class JoinGroupConsoleCommand implements ConsoleCommand {


    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("请输入要加入的gourp:");
        String groupId = scanner.next();
        JoinGroupRequestPacket packet = new JoinGroupRequestPacket();
        packet.setGroupId(groupId);
        channel.writeAndFlush(packet);
    }
}
