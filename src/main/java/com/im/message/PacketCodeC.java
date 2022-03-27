package com.im.message;

import com.im.message.packet.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import static com.im.message.command.Command.*;

/**
 * 封装消息格式协议
 */
public class PacketCodeC {
    public static final int MAGIC_NUMBER = 0x12345678;

    public static PacketCodeC INSTANCE = new PacketCodeC();

    public ByteBuf encode(ByteBufAllocator byteBufAllocator, Packet packet) {
        ByteBuf byteBuf = byteBufAllocator.DEFAULT.ioBuffer();
        return getByteBuf(packet, byteBuf);
    }

    private ByteBuf getByteBuf(Packet packet, ByteBuf byteBuf) {
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    public ByteBuf encode(ByteBuf byteBuf,Packet packet) {
        return getByteBuf(packet, byteBuf);
    }

    public Packet decode(ByteBuf byteBuf) {
        //魔数
        byteBuf.skipBytes(4);
        //版本号
        byteBuf.skipBytes(1);

        byte serializeAlgorithm = byteBuf.readByte();

        byte command = byteBuf.readByte();

        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);
        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }
        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm) {
        Serializer serializer = null;
        if (serializeAlgorithm == SerializerAlgorithm.JSON) {
            serializer = new JSONSerialzer();
        }
        return serializer;
    }

    private Class<? extends Packet> getRequestType(byte command) {
        Class clazz = null;
        if (LOGIN_REQUEST == command) {
            clazz = LoginRequestPacket.class;
        }
        if (LOGIN_RESPONSE == command) {
            clazz = LoginResponsePacket.class;
        }

        if (MESSAGE_REQUEST == command) {
            clazz = MessageRequestPacket.class;
        }

        if (MESSAGE_RESPONSE == command) {
            clazz = MessageResponsePacket.class;
        }

        if(GROUP_CREATE_REQUEST == command){
            clazz = CreateGroupRequestPacket.class;
        }

        if(GROUP_CREATE_RESPONSE == command){
            clazz = CreateGroupResponsePacket.class;
        }

        if(GROUP_JOIN_REQUEST == command){
            clazz = JoinGroupRequestPacket.class;
        }

        if(GROUP_JOIN_RESPONSE == command){
            clazz  = JoinGroupResponsePacket.class;
        }

        if(GROUP_QUIT_REQUEST== command){
            clazz  = QuitGroupRequestPacket.class;
        }

        if(GROUP_QUIT_RESPONSE== command){
            clazz  = QuitGroupResponsePacket.class;
        }

        return clazz;
    }
}
