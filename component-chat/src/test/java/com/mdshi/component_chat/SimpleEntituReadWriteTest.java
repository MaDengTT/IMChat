package com.mdshi.component_chat;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.mdshi.component_chat.room.IMDataBase;
import com.mdshi.component_chat.room.dao.MessageDao;
import com.mdshi.component_chat.room.entity.MessageEntity;
import com.mdshi.component_chat.room.entity.MessageListEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.Date;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

/**
 * Created by MaDeng on 2018/9/3.
 */
@RunWith(AndroidJUnit4.class)
public class SimpleEntituReadWriteTest {

    private MessageDao messageDao;
    private IMDataBase imDataBase;
    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        imDataBase = Room.inMemoryDatabaseBuilder(context, IMDataBase.class).build();
        messageDao = imDataBase.messageDao();
    }

    @After
    public void closeDb() {
        imDataBase.close();
    }

    private static final String TAG = "SimpleEntituReadWriteTe";
    @Test
    public void writeUserAndReadINlist() {
        MessageEntity entity = new MessageEntity();
        entity.content = "aaaa";
        entity.creatTime = new Date(System.currentTimeMillis());

        Flowable<List<MessageListEntity>> messageListAll = messageDao.getMessageListAll();
        messageListAll.subscribe(messageListEntity -> {
            Log.d(TAG, "writeUserAndReadINlist: "+messageListEntity.size());
        },throwable -> {
            Log.e(TAG, "writeUserAndReadINlist: ",throwable );
        });
    }
}
