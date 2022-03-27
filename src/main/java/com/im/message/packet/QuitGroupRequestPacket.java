package com.im.message.packet;

import static com.im.message.command.Command.GROUP_QUIT_REQUEST;

public class QuitGroupRequestPacket extends Packet {
    private String groupId;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public Byte getCommand() {
        return GROUP_QUIT_REQUEST;
    }
}
