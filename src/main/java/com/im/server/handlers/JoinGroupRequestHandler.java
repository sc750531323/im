package com.im.server.handlers;

import com.im.message.packet.JoinGroupRequestPacket;
import com.im.message.packet.JoinGroupResponsePacket;
import com.im.util.Session;
import com.im.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupRequestPacket packet) throws Exception {
        String groupId = packet.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        channelGroup.add(ctx.channel());
        JoinGroupResponsePacket joinGroupResponsePacket = new JoinGroupResponsePacket();
        joinGroupResponsePacket.setSuccess(true);
        joinGroupResponsePacket.setGroupId(groupId);
        SessionUtil.addSessionToGroup(groupId,SessionUtil.getSession(ctx.channel()));
        SessionUtil.addChannelToChannelGroup(groupId,ctx.channel(),ctx);
        joinGroupResponsePacket.setUserNames(SessionUtil.getAllUsernameByGroupId(groupId));
        joinGroupResponsePacket.setMessage(SessionUtil.getSession(ctx.channel()).getUserName() + "加入群【"+packet.getGroupId()+"】成功");
        channelGroup.writeAndFlush(joinGroupResponsePacket);
    }
}
