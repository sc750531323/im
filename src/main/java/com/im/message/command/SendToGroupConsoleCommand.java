package com.im.message.command;

import com.im.message.packet.SendToGroupRequestPacket;
import com.im.message.packet.SendToGroupResponsePacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class SendToGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("输入要发送的群groupId:");
        String groupId = scanner.next();
        System.out.println("输入要发送的消息：");
        String msg = scanner.next();
        SendToGroupRequestPacket packet = new SendToGroupRequestPacket();
        packet.setGroupId(groupId);
        packet.setMsg(msg);
        channel.writeAndFlush(packet);
    }
}
