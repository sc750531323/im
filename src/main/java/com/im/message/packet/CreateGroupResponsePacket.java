package com.im.message.packet;

import java.util.List;

import static com.im.message.command.Command.GROUP_CREATE_RESPONSE;

public class CreateGroupResponsePacket extends Packet {
    private String grouId;
    private Boolean success;
    private List<String> userIdList;
    private List<String> userNameList;

    public List<String> getUserNameList() {
        return userNameList;
    }

    public void setUserNameList(List<String> userNameList) {
        this.userNameList = userNameList;
    }

    public String getGrouId() {
        return grouId;
    }

    public void setGrouId(String grouId) {
        this.grouId = grouId;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<String> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<String> userIdList) {
        this.userIdList = userIdList;
    }

    @Override
    public Byte getCommand() {
        return GROUP_CREATE_RESPONSE;
    }
}
