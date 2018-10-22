package com.mdshi.common.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

import com.mdshi.common.db.bean.UserInfo;


/**
 * Created by MaDeng on 2018/9/19.
 */
@Entity(tableName = "tb_contacts",
        primaryKeys = {"id","contacts_id"},
        foreignKeys = {@ForeignKey(entity = UserEntity.class,
                parentColumns = "id",childColumns = "id",onDelete = ForeignKey.CASCADE
        )} //外键 消息列表ID
)
public class ContactsEntity {


    @ColumnInfo(name = "id")
    public long userId;
    @ColumnInfo(name = "contacts_id")
    public long contactsId;
    @ColumnInfo(name = "contacts_name")
    public String contactsName;

    public long groupId;

    public int status;

    public long session_id;

    @Embedded
    public UserInfo info;

    public long getSession_id() {
        if (userId > contactsId) {
            return Long.valueOf(userId + "" + contactsId);
        }else {
            return Long.valueOf(contactsId + "" + userId);
        }
    }


}
