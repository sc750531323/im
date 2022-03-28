package com.im.message.packet;

import com.im.message.command.Command;

public class HeartBeatRequestPacket extends Packet {
    private Long timestamp;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public Byte getCommand() {
        return Command.HEARTBEAT_REQUEST;
    }
}
