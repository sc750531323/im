package com.im.server.handlers;

import com.im.message.packet.Packet;
import com.im.message.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);
        out.add(packet);
    }
}
