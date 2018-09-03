package com.mdshi.component_chat.room.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.sql.Date;

/**
 * Created by MaDeng on 2018/9/3.
 */
@Entity(tableName = "tb_message",
        foreignKeys = {@ForeignKey(entity = MessageListEntity.class,
        parentColumns = "id",childColumns = "message_list_id",onDelete = ForeignKey.CASCADE
        )} //外键 消息列表ID

)
public class MessageEntity {
    @PrimaryKey
    public long id;    //id

    @ColumnInfo(name = "from_user_id")
    public long fUserId;   //发送方userID

    @ColumnInfo(name = "to_user_id")
    public long tUserId;   //接收方userId

    @ColumnInfo(name = "message_type")
    public int type;    //消息类型 0 表示文本

    @ColumnInfo(name = "content")
    public String content; //消息内容

    @ColumnInfo(name = "message_list_id")
    public long messageListId; //消息列表ID

    @ColumnInfo(name = "create_time")
    public Date creatTime;
}
