package com.mdshi.component_chat.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.mdshi.component_chat.room.dao.MessageDao;
import com.mdshi.component_chat.room.entity.MessageEntity;
import com.mdshi.component_chat.room.entity.MessageListEntity;

/**
 * Created by MaDeng on 2018/9/3.
 */
@Database(entities = {MessageEntity.class, MessageListEntity.class},version = 1)
public abstract class IMDataBase extends RoomDatabase {
    public abstract MessageDao messageDao();
}
