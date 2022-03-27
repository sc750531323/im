package com.im.server.handlers;

import com.im.message.packet.Packet;
import com.im.message.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 解码器有关的Handler都是不安全的，因为粘包拆包的缘故，Decoder必须要保存一些解析过程的中间状态，
 * 比如ByteToMessageDecoder类中维护的一个字节累加器cumulation，
 * 每次读到当前channel的消息后都会将消息累加到cumulation中，然后再调用子类实现的decode方法。
 */
//@ChannelHandler.Sharable
public class PacketDecoder extends ByteToMessageDecoder {
//    public static final PacketDecoder INSTANCE = new PacketDecoder();

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);
        out.add(packet);
    }
}
