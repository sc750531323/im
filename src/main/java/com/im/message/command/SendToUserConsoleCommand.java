package com.im.message.command;

import com.im.message.packet.MessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class SendToUserConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        MessageRequestPacket packet= new MessageRequestPacket();
        System.out.println("请输入需要接受的用户id：");
        String toUserId = scanner.nextLine();
        packet.setToUserId(toUserId);
        System.out.println("请输入想要输入的信息：");
        String message = scanner.nextLine();
        packet.setMessge(message);
        System.out.println("发送message：【"+message+"】给【"+toUserId+"】");
        channel.writeAndFlush(packet);
    }
}
