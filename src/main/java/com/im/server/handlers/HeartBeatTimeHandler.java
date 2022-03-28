package com.im.server.handlers;

import com.im.message.packet.HeartBeatRequestPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.TimeUnit;

public class HeartBeatTimeHandler extends ChannelInboundHandlerAdapter {
    private static final int HEARTBEAT_INTERVAL = 3;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        scheduleSendHeartbeat(ctx);
        super.channelActive(ctx);
    }

    private void scheduleSendHeartbeat(ChannelHandlerContext ctx){
        ctx.executor().schedule(() -> {
            if(ctx.channel().isActive()){
                HeartBeatRequestPacket heartBeatRequestPacket = new HeartBeatRequestPacket();
                heartBeatRequestPacket.setTimestamp(System.currentTimeMillis());
                System.out.println("客户端发送心跳数据");
                ctx.channel().writeAndFlush(heartBeatRequestPacket);
                scheduleSendHeartbeat(ctx);
            }
        },HEARTBEAT_INTERVAL, TimeUnit.SECONDS);
    }
}
