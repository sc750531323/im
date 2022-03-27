package com.im.server.handlers;

import com.im.message.packet.LoginRequestPacket;
import com.im.message.packet.LoginResponsePacket;
import com.im.util.Session;
import com.im.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

@ChannelHandler.Sharable
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
    public static final LoginResponseHandler INSTANCE = new LoginResponseHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket packet) throws Exception {
        if (packet.isSuccess()) {
            System.out.println(new Date() + ": 登录成功,您的用户id是："+packet.getUserId());
            SessionUtil.bindSession(new Session(packet.getUserId(),packet.getUserName()),ctx.channel());
        } else {
            System.out.println(new Date() + ": 登录失败了，原因是:" + packet.getReason());
        }
    }

//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("发送注册数据到server...");
//        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
//        loginRequestPacket.setUserId(UUID.randomUUID().toString());
//        loginRequestPacket.setName("flash");
//        loginRequestPacket.setPassword("pwd");
////        ctx.channel().writeAndFlush(loginRequestPacket);
//    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端连接被关闭！");
    }
}
