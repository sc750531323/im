package com.im.server.handlers;

import com.im.message.packet.CreateGroupResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 客户端接受到创建群组返回消息的处理器
 */
@ChannelHandler.Sharable
public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {

    public static final CreateGroupResponseHandler INSTANCE = new CreateGroupResponseHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupResponsePacket packet) throws Exception {
        System.out.println("加入群聊，群id为：【"+packet.getGrouId()+"】,群里有：");
        for (String s : packet.getUserNameList()) {
            System.out.println(s);
        }
    }
}
