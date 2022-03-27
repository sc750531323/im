package com.im.server.handlers;

import com.im.message.packet.JoinGroupResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class JoinGroupResponseHandler extends SimpleChannelInboundHandler<JoinGroupResponsePacket> {

    public static final JoinGroupResponseHandler INSTANCE = new JoinGroupResponseHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupResponsePacket packet) throws Exception {
        if (packet.isSuccess()) {
            System.out.println(packet.getMessage());
            System.out.println("当前群成员有：");
            for (String userName : packet.getUserNames()) {
                System.out.println(userName);
            }
        } else {
            System.out.println("加入群【" + packet.getGroupId() + "】失败");
        }
    }
}
