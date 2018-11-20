package com.mdshi.chatlib.Bean;

/**
 * Created by MaDeng on 2018/8/31.
 */
public class MessageBean {

    public String key;
    public String message;

    public static String CHAT_KEY = "11";
    public static String R_CHAT_KEY = "12"; //接收key
    public static String D_CHAT_KEY = "20"; //接收key后确认Key

    MessageBean() {

    }

    public MessageBean(String message) {
        key = message.substring(0,2);
        this.message = message.substring(2);
    }
    public MessageBean(String key,String message) {
        this.key = key;
        this.message = message;
    }
}
