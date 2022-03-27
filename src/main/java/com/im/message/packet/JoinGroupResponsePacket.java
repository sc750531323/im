package com.im.message.packet;

import com.im.util.Session;

import java.util.List;

import static com.im.message.command.Command.GROUP_JOIN_RESPONSE;

public class JoinGroupResponsePacket extends Packet {
    private boolean success;
    private String message;
    private String reason;
    private String groupId;
    private String newUser;
    private List<String> userNames;

    public String getNewUser() {
        return newUser;
    }

    public void setNewUser(String newUser) {
        this.newUser = newUser;
    }

    public List<String> getUserNames() {
        return userNames;
    }

    public void setUserNames(List<String> userNames) {
        this.userNames = userNames;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

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

    @Override
    public Byte getCommand() {
        return GROUP_JOIN_RESPONSE;
    }
}
