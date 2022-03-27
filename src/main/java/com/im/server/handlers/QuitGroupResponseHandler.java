package com.im.server.handlers;

import com.im.message.packet.QuitGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class QuitGroupResponseHandler extends SimpleChannelInboundHandler<QuitGroupResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupResponsePacket packet) throws Exception {
        System.out.println(packet.getMessage());
        System.out.println("当前群成员：");
        for (String username : packet.getUsesInGroup()) {
            System.out.println(username);
        }
    }
}
