package com.mdshi.chatlib.Bean;

/**
 * Created by MaDeng on 2018/11/20.
 */
public class TextMsg extends Message {
    private String contentText;

    public TextMsg() {
        type = MsgType.text;
    }

    public String getContentText() {
        return contentText;
    }

    public TextMsg setContentText(String contentText) {
        this.contentText = contentText;
        return this;
    }
}
