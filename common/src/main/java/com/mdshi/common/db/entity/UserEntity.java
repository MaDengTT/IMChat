package com.mdshi.common.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MaDeng on 2018/9/13.
 */
@Entity(tableName = "tb_user")
public class UserEntity {
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("userID")
    public long userId;

    @ColumnInfo(name = "name")
    public String userName;

    public String email;
    public String phone;
    public String avatar;
}
