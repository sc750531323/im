package com.im.message.packet;

import com.im.message.command.Command;

public class HeartBeatResponsePacket extends Packet{
    private Long serverTime;


    public Long getServerTime() {
        return serverTime;
    }

    public void setServerTime(Long serverTime) {
        this.serverTime = serverTime;
    }

    @Override
    public Byte getCommand() {
        return Command.HEARTBEAT_RESPONSE;
    }
}
