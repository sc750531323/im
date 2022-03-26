package com.im.message.packet;

import java.util.List;

import static com.im.message.command.Command.GROUP_CREATE_REQUEST;

public class CreateGroupRequestPacket extends Packet{
    private List<String> userIdList ;

    public List<String> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<String> userIdList) {
        this.userIdList = userIdList;
    }

    @Override
    public Byte getCommand() {
        return GROUP_CREATE_REQUEST;
    }
}
