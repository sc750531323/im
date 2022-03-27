package com.im.server.handlers;

import com.im.message.packet.SendToGroupResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class SendToGroupResponseHandler extends SimpleChannelInboundHandler<SendToGroupResponsePacket> {
    public static final SendToGroupResponseHandler INSTANCE = new SendToGroupResponseHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SendToGroupResponsePacket msg) throws Exception {
        System.out.println(msg.getMessage());
    }
}
