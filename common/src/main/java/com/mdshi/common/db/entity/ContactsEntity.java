package com.mdshi.common.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;


/**
 * Created by MaDeng on 2018/9/19.
 */
@Entity(tableName = "tb_contacts",
        primaryKeys = {"user_id","contacts_id"},
        foreignKeys = {@ForeignKey(entity = UserEntity.class,
                parentColumns = "id",childColumns = "user_id",onDelete = ForeignKey.CASCADE
        )} //外键 消息列表ID
)
public class ContactsEntity {


    @ColumnInfo(name = "user_id")
    public long userId;
    @ColumnInfo(name = "contacts_id")
    public long contactsId;
    @ColumnInfo(name = "contacts_name")
    public String contactsName;

    public long groupId;

    public int status;

    public long session_id;

    @Embedded
    public UserEntity info;

    public long getSession_id() {
        if (userId > contactsId) {
            return Long.valueOf(userId + "" + contactsId);
        }else {
            return Long.valueOf(contactsId + "" + userId);
        }
    }


}
