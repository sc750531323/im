package com.im.message.packet;

import com.im.message.command.Command;

public class SendToGroupRequestPacket extends Packet {

    private String groupId;
    private String msg;


    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public Byte getCommand() {
        return Command.GROUP_SEND_REQUEST;
    }
}
