package com.im.message.packet;

import static com.im.message.command.Command.MESSAGE_REQUEST;

public class MessageRequestPacket extends Packet {

    private String messge;
    private String toUserId;

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getMessge() {
        return messge;
    }

    public void setMessge(String messge) {
        this.messge = messge;
    }

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
