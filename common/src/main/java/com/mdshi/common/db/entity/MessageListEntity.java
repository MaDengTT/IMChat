package com.mdshi.common.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.ColorInt;

import java.util.Date;


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


    public long other_Id;   //对方ID

    @ColumnInfo(name = "session_type")
    public int sessionType; //会话类型 0 表示好友聊天

    @ColumnInfo(name = "new_date")
    public Date newDate;    //最新更新时间 用于排序

    @ColumnInfo(name = "unread_num")
    public int unReadNum;   //未读消息数

    @ColumnInfo(name = "new_message_id")
    public int newMessageId; //最新消息ID
//    @Embedded
//    public MessageEntity newMessage;
}
