package com.mdshi.chatlib.Bean;

/**
 * Created by MaDeng on 2018/9/3.
 */
public class SendMessage {
    public String key;
    public String body;

    public String from;
    public String to;

    public int messageType;
    public int chatType;

    public static int MSG_TYPE_TEXT = 0; //文字
    public static int CHAT_TYPE_ONE = 2; //私聊
}
