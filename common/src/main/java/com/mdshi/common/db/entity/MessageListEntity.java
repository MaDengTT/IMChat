package com.mdshi.common.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.ColorInt;

import java.util.Date;

import dagger.Component;


/**
 * Created by MaDeng on 2018/9/3.
 */
@Entity(tableName = "tb_message_list"

)
public class MessageListEntity {
    @PrimaryKey
    public long id;     //ID 是服务端返回的

//    @ColumnInfo(name = "session_id")
//    public long sessionId; //会话ID sessionType 为0时 表示好友ID+我的ID long+long 为运算

    @ColumnInfo(name = "user_id")
    public long user_Id;

    public long other_Id;   //对方ID

    @ColumnInfo(name = "session_type")
    public int sessionType; //会话类型 0 表示好友聊天

    @ColumnInfo(name = "new_date")
    public Date newDate;    //最新更新时间 用于排序

    @ColumnInfo(name = "unread_num")
    public int unReadNum;   //未读消息数

    @ColumnInfo(name = "new_message_id")
    public int newMessageId; //最新消息ID

    @ColumnInfo(name = "new_message_content")
    public String newMessageContent;

    @ColumnInfo(name = "avatar_url")
    public String avatarUrl;
    @ColumnInfo(name = "message_name")
    public String name;
//    @Embedded
//    public MessageEntity newMessage;

    public void createBean(long id, long user_Id, long other_Id, int sessionType) {

        this.id = id;
        this.user_Id = user_Id;
        this.other_Id = other_Id;
        this.sessionType = sessionType;
        unReadNum = 0;
    }

    public void updateBean(Date newDate, int newMessageId,String newMessageContent) {
        this.newDate = newDate;
        this.newMessageId = newMessageId;
        this.newMessageContent = newMessageContent;
        unReadNum++;
    }

    public void setUserInfo(String name,String url) {
        this.avatarUrl = url;
    }
    @Override
    public String toString() {
        return "MessageListEntity{" +
                "id=" + id +
                ", user_Id=" + user_Id +
                ", other_Id=" + other_Id +
                ", sessionType=" + sessionType +
                ", newDate=" + newDate +
                ", unReadNum=" + unReadNum +
                ", newMessageId=" + newMessageId +
                '}';
    }
}
