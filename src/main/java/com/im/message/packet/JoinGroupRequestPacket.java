package com.im.message.packet;

import static com.im.message.command.Command.GROUP_JOIN_REQUEST;

public class JoinGroupRequestPacket extends Packet {

    private String groupId;
    private String myUserId;

    public String getMyUserId() {
        return myUserId;
    }

    public void setMyUserId(String myUserId) {
        this.myUserId = myUserId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public Byte getCommand() {
        return GROUP_JOIN_REQUEST;
    }
}
