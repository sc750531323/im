package com.im.message.packet;

import java.util.List;

import static com.im.message.command.Command.GROUP_QUIT_RESPONSE;

public class QuitGroupResponsePacket extends Packet{
    private String message;
    private String reason;
    private boolean success;
    private List<String> usesInGroup;

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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<String> getUsesInGroup() {
        return usesInGroup;
    }

    public void setUsesInGroup(List<String> usesInGroup) {
        this.usesInGroup = usesInGroup;
    }

    @Override
    public Byte getCommand() {
        return GROUP_QUIT_RESPONSE;
    }
}
