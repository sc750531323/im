package com.im.message.packet;

import static com.im.message.command.Command.MESSAGE_RESPONSE;

public class MessageResponsePacket extends Packet{
    private String message;
    private String fromUserId;
    private String fromUserName;

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return MESSAGE_RESPONSE;
    }
}
