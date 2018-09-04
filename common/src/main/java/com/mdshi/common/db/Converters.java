package com.mdshi.common.db;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by MaDeng on 2018/9/4.
 */
public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value){
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date value) {
        return value == null ? null : value.getTime();
    }
}
