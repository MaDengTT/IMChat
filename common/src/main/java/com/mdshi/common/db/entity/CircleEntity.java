package com.mdshi.common.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

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

    @ColumnInfo(name = "create_time")
    public long createTime;
}
