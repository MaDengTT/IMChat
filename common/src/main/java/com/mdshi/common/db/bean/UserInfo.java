package com.mdshi.common.db.bean;

import android.arch.persistence.room.ColumnInfo;

/**
 * Created by MaDeng on 2018/10/22.
 */
public class UserInfo {
    @ColumnInfo(name = "user_id")
    public long userId;

    @ColumnInfo(name = "user_name")
    public String userName;
    @ColumnInfo(name = "user_email")
    public String email;
    @ColumnInfo(name = "user_phone")
    public String phone;
    @ColumnInfo(name = "user_avatar")
    public String avatar;
}
