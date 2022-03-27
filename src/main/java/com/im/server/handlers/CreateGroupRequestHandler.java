package com.im.server.handlers;

import com.im.message.packet.CreateGroupRequestPacket;
import com.im.message.packet.CreateGroupResponsePacket;
import com.im.util.Session;
import com.im.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ChannelHandler.Sharable
public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {
    public static final CreateGroupRequestHandler INSTANCE = new CreateGroupRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket packet) throws Exception {
        List<String> userIdList = packet.getUserIdList();
        List<String> userNameList = new ArrayList<>();
        List<Session> sessionList = new ArrayList<>();

        //1.创建channel分组
        ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());

        for (String userId : userIdList) {
            Channel channel = SessionUtil.getChannel(userId);
            if (channel != null) {
                channelGroup.add(channel);
                String userName = SessionUtil.getUsernameByUserId(userId);
                userNameList.add(userName);
                sessionList.add(new Session(userId, userName));
            }
        }
        //创建群聊创建相应结果
        CreateGroupResponsePacket createGroupResponsePacket = new CreateGroupResponsePacket();
        String groupId = UUID.randomUUID().toString();
        createGroupResponsePacket.setGrouId(groupId);
        createGroupResponsePacket.setSuccess(true);
        createGroupResponsePacket.setUserIdList(userIdList);
        createGroupResponsePacket.setUserNameList(userNameList);
        //给组内的每个channel都写入packet
        channelGroup.writeAndFlush(createGroupResponsePacket);
        SessionUtil.createGroup(groupId, channelGroup);
        SessionUtil.addSessionToGroup(groupId, sessionList);
        System.out.println("群创建成功，id 是：【" + groupId + "】");
        System.out.println("群里面有" + SessionUtil.getAllUsernameByGroupId(groupId));
    }
}
