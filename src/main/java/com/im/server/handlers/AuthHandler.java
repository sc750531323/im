package com.im.server.handlers;

import com.im.util.LoginUtil;
import com.im.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class AuthHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!SessionUtil.hasLogin(ctx.channel())) {
            ctx.channel().close();
        } else {
            System.out.println("移除auth handler，当前channel："+ctx.channel());
            ctx.pipeline().remove(this);
        }
        super.channelRead(ctx, msg);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if (LoginUtil.hasLogin(ctx.channel())) {
            System.out.println("当前连接已经验证完毕，authhandler会被移除。");
        } else {
            System.out.println("无登录验证，强制关闭连接！");
        }
    }
}
