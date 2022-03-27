package com.im.message.packet;

import com.im.message.command.Command;

public class SendToGroupResponsePacket extends Packet {
    private String message ;
    private String reason;
    private Boolean success;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @Override
    public Byte getCommand() {
        return Command.GROUP_SEND_RESPONSE;
    }
}
