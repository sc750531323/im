package com.im.server.handlers;

import com.im.message.packet.CreateGroupRequestPacket;
import com.im.message.packet.CreateGroupResponsePacket;
import com.im.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket packet) throws Exception {
        List<String> userIdList = packet.getUserIdList();
        List<String> userNameList = new ArrayList<>();

        //1.创建channel分组
        ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());

        for (String userId : userIdList) {
            Channel channel = SessionUtil.getChannel(userId);
            if(channel != null){
                channelGroup.add(channel);
                userNameList.add(SessionUtil.getSession(channel).getUserName());
            }
        }

        //创建群聊创建相应结果
        CreateGroupResponsePacket createGroupResponsePacket = new CreateGroupResponsePacket();
        createGroupResponsePacket.setGrouId(UUID.randomUUID().toString());
        createGroupResponsePacket.setSuccess(true);
        createGroupResponsePacket.setUserIdList(userIdList);
        //给组内的每个channel都写入packet
        channelGroup.writeAndFlush(createGroupResponsePacket);

        System.out.println("群创建成功，id 是：【"+createGroupResponsePacket.getGrouId()+"】");
        System.out.println("群里面有【"+createGroupResponsePacket.getUserIdList()+"】");

    }
}
