package com.im.server.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class InBoundHandlerA extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("InBoundHandlerA:"+msg);
        //父类调用的是ctx.fireChannelRead(msg);从而把数据发送到下一个handler
        super.channelRead(ctx, msg);
    }

}
