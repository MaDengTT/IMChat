package com.mdshi.common.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by MaDeng on 2018/9/19.
 */
@Entity(tableName = "tb_contacts",
        primaryKeys = {"userid","contactsid"},
        foreignKeys = {@ForeignKey(entity = UserEntity.class,
                parentColumns = "id",childColumns = "userid",onDelete = ForeignKey.CASCADE
        )} //外键 消息列表ID
)
public class ContactsEntity {


    @ColumnInfo(name = "userid")
    public long userId;
    @ColumnInfo(name = "contactsid")
    public long contactsId;
    @ColumnInfo(name = "name")
    public String contactsName;

    public long groupId;

    public String avatar;

    public int status;

    public long session_id;

}
