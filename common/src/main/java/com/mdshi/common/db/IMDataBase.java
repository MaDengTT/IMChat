package com.mdshi.common.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import com.mdshi.common.db.dao.ContactsDao;
import com.mdshi.common.db.dao.MessageDao;
import com.mdshi.common.db.dao.UserDao;
import com.mdshi.common.db.entity.MessageEntity;
import com.mdshi.common.db.entity.MessageListEntity;
import com.mdshi.common.db.entity.UserEntity;

/**
 * Created by MaDeng on 2018/9/3.
 */
@Database(entities = {MessageEntity.class, MessageListEntity.class, UserEntity.class},version = 1)
@TypeConverters({Converters.class})
public abstract class IMDataBase extends RoomDatabase {
    public abstract MessageDao messageDao();

    public abstract UserDao userDao();

    public abstract ContactsDao contactsDao();
}
