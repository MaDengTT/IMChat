package com.mdshi.common.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.mdshi.common.db.entity.MessageEntity;
import com.mdshi.common.db.entity.MessageListEntity;

import java.util.List;

@Dao
public interface MessageListDao {

    @Query("SELECT ml.id as sessionId,ml.unread_num as unReadNum,ml.name as userName,ml.avatar as avatar," +
            "m.create_time as time,m.content as content" +
            " FROM ( SELECT create_time,content,session_id FROM tb_message ) as m" +
            " INNER JOIN tb_message_list as ml ON m.session_id = ml.id" +
            " WHERE ml.user_id in (:userId) " +
            "ORDER BY m.create_time DESC")
    LiveData<List<MessageListEntity>> getAll(long userId);

    @Query("")

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertMessageList(MessageListEntity... messageListEntity);

    @Delete
    public abstract void deleteMessageList(MessageListEntity entity);

    @Update
    public abstract void updateMessageList(MessageListEntity entity);

}

