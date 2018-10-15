package com.mdshi.common.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;


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

    public static class UserInfo {
        @ColumnInfo(name = "id")
        public long userID;
        public String userName;
        public String email;
        public String phone;
        public String avatar;
    }

}
