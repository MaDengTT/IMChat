package com.mdshi.common;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.mdshi.common.db.IMDataBase;
import com.mdshi.common.db.dao.MessageDao;
import com.mdshi.common.db.entity.MessageEntity;
import com.mdshi.common.db.entity.MessageListEntity;

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
public class SimpleEntityReadWriteTest {

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

        entity.tUserId=123;
        entity.fUserId=456;
        entity.session_id=154564678;
        entity.content = "aaaa";
        entity.createTime = new Date(System.currentTimeMillis());

//        Log.d(TAG, "writeUserAndReadINlist: ");
        MessageListEntity entity1 = new MessageListEntity();
        entity1.id = 154564678;
        messageDao.insertMessageList(entity1);
        messageDao.insertMessage(entity);
//        messageDao.insertMessageUpdateMessageList(entity);

        final Flowable<List<MessageListEntity>> messageListAll = messageDao.getMessageListAll();
        messageListAll.subscribe(new Consumer<List<MessageListEntity>>() {
            @Override
            public void accept(List<MessageListEntity> messageListEntities) throws Exception {
                Log.d(TAG, "accept: "+messageListEntities.size());
                System.out.println("a"+messageListEntities.size());
            }
        }/*, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e(TAG,throwable.toString());
            }
        }*/);
    }
}
