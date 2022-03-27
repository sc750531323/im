package com.im.message.command;

public interface Command {
    Byte LOGIN_REQUEST = 1;
    Byte LOGIN_RESPONSE = 2;
    Byte MESSAGE_REQUEST = 3;
    Byte MESSAGE_RESPONSE = 4;
    Byte GROUP_CREATE_REQUEST = 5;
    Byte GROUP_CREATE_RESPONSE = 6;
    Byte GROUP_JOIN_REQUEST = 7;
    Byte GROUP_JOIN_RESPONSE = 8;
    Byte GROUP_QUIT_REQUEST = 9;
    Byte GROUP_QUIT_RESPONSE = 10;
    Byte GROUP_SEND_REQUEST = 11;
    Byte GROUP_SEND_RESPONSE = 12;

}
