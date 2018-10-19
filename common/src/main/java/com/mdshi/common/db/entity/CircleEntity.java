package com.mdshi.common.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

import java.util.Date;
import java.util.List;

/**
 * Created by MaDeng on 2018/10/17.
 */
@Entity(tableName = "tb_circle",
        primaryKeys = {"id"}
)
public class CircleEntity {

    public long id;
    public long userid;
    public String username;
    public String avatar;
    public String contnent;
    public String images;

    public CircleEntity(long id, long userid, String username, String avatar, String contnent, String images, Date createTime) {
        this.id = id;
        this.userid = userid;
        this.username = username;
        this.avatar = avatar;
        this.contnent = contnent;
        this.images = images;
        this.createTime = createTime;
    }

    @ColumnInfo(name = "create_time")
    public Date createTime;
}
