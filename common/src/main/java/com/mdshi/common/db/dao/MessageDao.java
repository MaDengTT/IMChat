package com.mdshi.common.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;
import android.os.Message;

import com.mdshi.common.db.entity.MessageEntity;
import com.mdshi.common.db.entity.MessageListEntity;

import org.reactivestreams.Publisher;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * Created by MaDeng on 2018/9/3.
 */
@Dao
public abstract class MessageDao {



    @Query("SELECT* FROM tb_message WHERE session_id IN (:id) ORDER BY create_time DESC LIMIT (:pageSize) offset (:pageSize-1)*(:pageNo)")
    public abstract Flowable<List<MessageEntity>> getMessageToFlowableById(long id,int pageSize,int pageNo);

    @Query("SELECT* FROM tb_message WHERE session_id IN (:id) ORDER BY create_time DESC LIMIT (:pageSize) offset (:pageNo)")
    public abstract List<MessageEntity> getMessageById(long id,int pageSize,int pageNo);

    //根据会话ID获取条目
    @Query("SELECT * From tb_message_list WHERE id IN(:sessionId) LIMIT 1")
    public abstract MessageListEntity getMsgListBySessionID(long sessionId);


    @Query("SELECT * from tb_message WHERE session_id IN(:sessionId)ORDER BY create_time DESC LIMIT 1")
    public abstract Flowable<MessageEntity> getMsgByNewDate(long sessionId);

    @Query("SELECT * from tb_message WHERE session_id IN(:sessionId)ORDER BY create_time DESC")
    public abstract DataSource.Factory<Integer,MessageEntity> getMsgDataAll(long sessionId);

    @Insert
    public abstract void insertMessage(MessageEntity... messageEntities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertMessageList(MessageListEntity... messageListEntity);

    @Delete
    public abstract void deleteMessage(MessageEntity entity);
    @Delete
    public abstract void deleteMessageList(MessageListEntity entity);

    @Update
    public abstract void updateMessageList(MessageListEntity entity);



    @Transaction
    public void insertMessageListAndMessage(MessageListEntity entity, MessageEntity entity2) {
        insertMessageList(entity);
        insertMessage(entity2);
    }
}
