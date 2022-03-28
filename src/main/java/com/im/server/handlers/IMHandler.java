package com.im.server.handlers;

import com.im.message.command.Command;
import com.im.message.packet.Packet;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Map;

@ChannelHandler.Sharable
public class IMHandler extends SimpleChannelInboundHandler<Packet> {
    public static final IMHandler INSTANCE = new IMHandler();

    private Map<Byte,SimpleChannelInboundHandler<? extends  Packet>> handlerMap;

    private IMHandler(){
        this.handlerMap = new HashMap<>();
        handlerMap.put(Command.GROUP_CREATE_REQUEST,CreateGroupRequestHandler.INSTANCE);
        handlerMap.put(Command.GROUP_JOIN_REQUEST,JoinGroupRequestHandler.INSTANCE);
        handlerMap.put(Command.GROUP_QUIT_REQUEST,QuitGroupRequestHandler.INSTANCE);
        handlerMap.put(Command.GROUP_SEND_REQUEST,SendToGroupRequestHandler.INSTANCE);
        handlerMap.put(Command.MESSAGE_REQUEST,MessageRequestHandler.INSTANCE);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        handlerMap.get(packet.getCommand()).channelRead(ctx,packet);
    }
}
