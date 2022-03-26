package com.im.server.handlers;

import com.im.message.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class Spliter extends LengthFieldBasedFrameDecoder {
    private static final int FIELD_OFFSET = 7;
    private static final int FIELD_LENGTH= 4;

    public Spliter() {
        super(Integer.MAX_VALUE,FIELD_OFFSET,FIELD_LENGTH);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        if(in.getInt(in.readerIndex()) != PacketCodeC.MAGIC_NUMBER){
            ctx.channel().close();
            return  null;
        }
        return super.decode(ctx,in);
    }
}
