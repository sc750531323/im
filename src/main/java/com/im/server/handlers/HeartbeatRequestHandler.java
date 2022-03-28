package com.im.server.handlers;

import com.im.message.packet.HeartBeatRequestPacket;
import com.im.message.packet.HeartBeatResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class HeartbeatRequestHandler extends SimpleChannelInboundHandler<HeartBeatRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatRequestPacket msg) throws Exception {
        System.out.println("收到客户端心跳："+msg.getTimestamp());
        HeartBeatResponsePacket heartBeatResponsePacket = new HeartBeatResponsePacket();
        heartBeatResponsePacket.setServerTime(System.currentTimeMillis());
        ctx.channel().writeAndFlush(heartBeatResponsePacket);
    }
}
