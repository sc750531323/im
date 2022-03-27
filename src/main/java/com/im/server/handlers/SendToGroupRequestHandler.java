package com.im.server.handlers;

import com.im.message.packet.SendToGroupRequestPacket;
import com.im.message.packet.SendToGroupResponsePacket;
import com.im.util.Session;
import com.im.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

public class SendToGroupRequestHandler extends SimpleChannelInboundHandler<SendToGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SendToGroupRequestPacket packet) throws Exception {
        String groupId = packet.getGroupId();
        String msg = packet.getMsg();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        Session session = SessionUtil.getSession(ctx.channel());
        String reponseMsg = "【" + session.getUserName() + "】 发来一条群消息:"+msg;
        SendToGroupResponsePacket sendToGroupResponsePacket = new SendToGroupResponsePacket();
        sendToGroupResponsePacket.setMessage(reponseMsg);
        sendToGroupResponsePacket.setSuccess(true);
        channelGroup.writeAndFlush(sendToGroupResponsePacket);
    }
}
