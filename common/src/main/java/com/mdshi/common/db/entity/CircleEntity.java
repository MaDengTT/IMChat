package com.mdshi.common.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;

import java.util.Date;

/**
 * Created by MaDeng on 2018/10/17.
 */
@Entity(tableName = "user_id",
        primaryKeys = {"id"}
)
public class CircleEntity {

    @ColumnInfo(name = "circle_id")
    public long id;
    @ColumnInfo(name = "circle_content")
    public String content;
    @ColumnInfo(name = "circle_images")
    public String images;

    @Embedded
    public UserEntity userInfo;

    @ColumnInfo(name = "circle_create_time")
    public Date createTime;
}
