package com.mdshi.component_chat.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.os.Message;

import com.mdshi.component_chat.room.entity.MessageEntity;
import com.mdshi.component_chat.room.entity.MessageListEntity;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by MaDeng on 2018/9/3.
 */
@Dao
public interface MessageDao {
    @Query("SELECT* FROM tb_message_list ORDER BY new_date DESC")
    Flowable<List<MessageListEntity>> getMessageListAll();

    @Query("SELECT* FROM tb_message WHERE message_list_id IN (:id) ORDER BY create_time DESC LIMIT (:pageSize) offset (:pageNo)")
    Flowable<List<MessageEntity>> getMessageById(long id,int pageSize,int pageNo);

    @Insert
    void insertMessage(MessageEntity... messageEntities);

    @Insert
    void insertMessageList(MessageListEntity messageListEntity);

    @Delete
    void deleteMessage(MessageEntity entity);

    @Update
    void updateMessageList(MessageListEntity entity);
}
