package com.mdshi.component_chat.room.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.ColorInt;

import java.sql.Date;

/**
 * Created by MaDeng on 2018/9/3.
 */
@Entity(tableName = "tb_message_list",
        foreignKeys = {@ForeignKey(entity = MessageEntity.class,
                parentColumns = "id",childColumns = "new_message_id")
        }
)
public class MessageListEntity {
    @PrimaryKey
    public long id;

    @ColumnInfo(name = "session_id")
    public long sessionId; //会话ID 0 表示好友ID

    @PrimaryKey
    @ColumnInfo(name = "session_type")
    public int sessionType; //会话类型 0 表示好友聊天

    @ColumnInfo(name = "new_date")
    public Date newDate;    //最新更新时间 用于排序

    @ColumnInfo(name = "unread_num")
    public int unReadNum;   //未读消息数

    @ColumnInfo(name = "new_message_id")
    public long newMessageId; //最新消息ID
//    @Embedded
//    public MessageEntity newMessage;
}
