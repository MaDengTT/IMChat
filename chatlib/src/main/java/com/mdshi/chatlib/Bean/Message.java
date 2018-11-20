package com.mdshi.chatlib.Bean;

/**
 * Created by MaDeng on 2018/11/20.
 */
public class Message {
    public String from;
    public String to;
    public MsgDirection direction;
    public MsgType type;
    public MsgStatus status;

    public enum MsgDirection{
        In,
        Out
    }

    public  enum MsgType{
        text(0),
        file(1),
        audio(2),
        image(3),
        video(4),
        notification(5);
        private int value;

        MsgType(int value) {
            this.value = value;
        }

        int getValue() {
            return value;
        }
    }

    public enum MsgStatus{
        draft,
        fail,
        read,
        sending,
        success,
        unread
    }
}
