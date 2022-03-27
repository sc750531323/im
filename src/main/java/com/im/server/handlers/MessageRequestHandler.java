package com.im.server.handlers;

import com.im.message.packet.MessageRequestPacket;
import com.im.message.packet.MessageResponsePacket;
import com.im.util.Session;
import com.im.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    public static final MessageRequestHandler INSTANCE = new MessageRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {
        String message = messageRequestPacket.getMessge();
        String toUserId = messageRequestPacket.getToUserId();
        Session session = SessionUtil.getSession(ctx.channel());

        System.out.println("服务端收到message类型的packet，内容是：" + message);
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUserName());
        messageResponsePacket.setMessage(message);

        Channel channel = SessionUtil.getChannel(toUserId);
        if(SessionUtil.getChannel(toUserId) != null && SessionUtil.hasLogin(channel)){
            channel.writeAndFlush(messageResponsePacket);
        } else {
            System.out.println("用户【"+toUserId+"】不在线，发送失败！");
        }
    }
}
