package com.im.server.handlers;

import com.im.message.packet.QuitGroupRequestPacket;
import com.im.message.packet.QuitGroupResponsePacket;
import com.im.util.Session;
import com.im.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

@ChannelHandler.Sharable
public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {
    public static QuitGroupRequestHandler INSTANCE = new QuitGroupRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupRequestPacket packet) throws Exception {
        String groupId = packet.getGroupId();
        Session session = SessionUtil.getSession(ctx.channel());
        //
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        channelGroup.remove(ctx.channel());

        SessionUtil.removeSessionByGroupId(groupId, session);

        QuitGroupResponsePacket quitGroupResponsePacket = new QuitGroupResponsePacket();
        quitGroupResponsePacket.setSuccess(true);
        quitGroupResponsePacket.setMessage("【" + session.getUserName() + "】骂骂咧咧的退出了群聊");
        quitGroupResponsePacket.setUsesInGroup(SessionUtil.getAllUsernameByGroupId(groupId));

        ctx.channel().writeAndFlush(quitGroupResponsePacket);
        channelGroup.writeAndFlush(quitGroupResponsePacket);
    }
}
