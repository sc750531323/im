package com.im.server.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class ImIdleStateHandler extends IdleStateHandler {
    private static final int READER_IDLE_TIME = 15;

    public ImIdleStateHandler(){
        super(READER_IDLE_TIME,0,0, TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        System.out.println(READER_IDLE_TIME + "秒内没有读到数据，连接关闭！");
        ctx.channel().close();
    }
}
