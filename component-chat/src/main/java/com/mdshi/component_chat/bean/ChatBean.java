package com.mdshi.component_chat.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.Date;

/**
 * Created by MaDeng on 2018/9/3.
 */
public class ChatBean implements MultiItemEntity {

    public long session_id;

    public enum Type {
        TEXT_L,
        TEXT_R,
        IMAGE_L,
        IMAGE_R,
        VIDEO_L,
        VIDEO_R,
        VOICE_L,
        VOICE_R,
        WEB_L,
        WEB_R,
    }

    public Type type;

    public String content;

    public Date date;

    public long userId;

    public long tUserId;

//    public String avatar;


    @Override
    public String toString() {
        return "ChatBean{" +
                "session_id=" + session_id +
                ", type=" + type +
                ", content='" + content + '\'' +
                ", date=" + date +
                ", userId=" + userId +
                ", tUserId=" + tUserId +
                '}';
    }

    public ChatBean(long userId) {
        this.userId = userId;
    }

    @Override
    public int getItemType() {
        return type.ordinal();
    }
}
