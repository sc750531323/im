package com.im.server.handlers;

import com.im.message.packet.HeartBeatResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class HeartbeatResponseHandler extends SimpleChannelInboundHandler<HeartBeatResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatResponsePacket msg) throws Exception {
        System.out.println("收到服务端返回心跳数据:"+msg.getServerTime());
    }
}
