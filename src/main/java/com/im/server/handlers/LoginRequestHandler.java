package com.im.server.handlers;

import com.im.message.packet.LoginRequestPacket;
import com.im.message.packet.LoginResponsePacket;
import com.im.util.Session;
import com.im.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

//表示该handler可以被多个channel共享
@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket packet) throws Exception {
        String userName = packet.getName();
        System.out.println("服务端收到注册数据,用户名："+packet.getName());
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        String userId = UUID.randomUUID().toString();
        loginResponsePacket.setVersion(packet.getVersion());
        loginResponsePacket.setSuccess(true);
        loginResponsePacket.setUserId(userId);
        loginResponsePacket.setUserName(userName);
        SessionUtil.bindSession(new Session(userId,userName),ctx.channel());
        SessionUtil.addLoginUser(userId,userName);
        ctx.channel().writeAndFlush(loginResponsePacket);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
    }
}
