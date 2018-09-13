package com.mdshi.common.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by MaDeng on 2018/9/13.
 */
@Entity(tableName = "tb_user")
public class UserEntity {
    @PrimaryKey
    public long id;

    public String email;
    public String phone;
    public String name;
    public String avatar;
}
