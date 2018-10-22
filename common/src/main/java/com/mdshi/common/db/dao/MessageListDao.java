package com.mdshi.common.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.mdshi.common.db.bean.MessageListBean;
import com.mdshi.common.db.entity.MessageEntity;
import com.mdshi.common.db.entity.MessageListEntity;

import java.util.List;

@Dao
public interface MessageListDao {

    @Query("SELECT ml.id as sessionId," +
            "ml.unread_num as unReadNum," +
            "c.contacts_id as contactsId," +
            "c.contacts_name as contactsName," +
            "c.user_name as userName," +
            "c.user_avatar as contactsAvatar," +
            "m.content as content," +
            "m.create_time as createTime FROM " +
            "(SELECT * FROM " +
                "(SELECT m.session_id,m.content,m.create_time FROM tb_message AS m ORDER BY m.create_time ASC) " +
                    "as m GROUP BY m.session_id) AS m " +
            "INNER JOIN tb_message_list as ml ON m.session_id = ml.id " +
            "INNER JOIN tb_contacts as c ON ml.msg_contacts_id = c.contacts_id " +
            "WHERE ml.user_id in (:userId) " +
            "ORDER BY m.create_time DESC")
    LiveData<List<MessageListBean>> getAll(long userId);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertMessageList(MessageListEntity... messageListEntity);

    @Delete
    public abstract void deleteMessageList(MessageListEntity entity);

    @Update
    public abstract void updateMessageList(MessageListEntity entity);

}

