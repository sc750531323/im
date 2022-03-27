package com.im.message.command;

import com.im.message.packet.QuitGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * 退出群聊
 */
public class QuitGroupConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("请输入要退出的groupId：");
        String groupIdToQuit = scanner.next();
        QuitGroupRequestPacket quitGroupRequestPacket = new QuitGroupRequestPacket();
        quitGroupRequestPacket.setGroupId(groupIdToQuit);
        channel.writeAndFlush(quitGroupRequestPacket);
    }
}
