package com.mdshi.common.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.alibaba.android.arouter.facade.annotation.Autowired;

import java.util.Date;


/**
 * Created by MaDeng on 2018/9/3.
 */
@Entity(tableName = "tb_message",
        indices = @Index("session_id"),
        foreignKeys = {@ForeignKey(entity = MessageListEntity.class,
        parentColumns = "id",childColumns = "session_id",onDelete = ForeignKey.CASCADE
        )} //外键 消息列表ID

)

public class MessageEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;    //id

    @ColumnInfo(name = "from_user_id")
    public long fUserId;   //发送方userID

    @ColumnInfo(name = "to_user_id")
    public long tUserId;   //接收方userId

    @ColumnInfo(name = "message_type")
    public int type;    //消息类型 0 表示文本

    @ColumnInfo(name = "content")
    public String content; //消息内容

    @ColumnInfo(name = "session_id")
    public long session_id; //会话列表ID 对应messagelistId

    @ColumnInfo(name = "create_time")
    public Date createTime;

    @Ignore
    public long other_id; //对方ID 如果为接受就是 fUserId；如果发送就是 to_user_Id
}
