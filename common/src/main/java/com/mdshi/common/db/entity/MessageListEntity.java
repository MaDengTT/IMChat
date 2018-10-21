package com.mdshi.common.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;



/**
 * Created by MaDeng on 2018/9/3.
 */
@Entity(tableName = "tb_message_list"

)
public class MessageListEntity {
    @PrimaryKey
    public long id;     //ID 是服务端返回的

    @ColumnInfo(name = "user_id")
    public long user_Id;

    @ColumnInfo(name = "unread_num")
    public int unReadNum;   //未读消息数

    @Embedded
    public UserEntity userInfo;

}
