package com.mdshi.component_chat;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.mdshi.common.db.IMDataBase;
import com.mdshi.common.db.dao.MessageDao;
import com.mdshi.common.db.entity.MessageListEntity;
import com.mdshi.component_chat.data.ChatRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

import static org.junit.Assert.assertEquals;

/**
 * Created by MaDeng on 2018/9/5.
 */
@RunWith(AndroidJUnit4.class)
public class LiveDataTest {

    private MessageDao messageDao;
    private IMDataBase imDataBase;
    private static final String TAG = "LiveDataTest";
    @Before
    public void create() {
        Context context = InstrumentationRegistry.getTargetContext();
        imDataBase = Room.inMemoryDatabaseBuilder(context, IMDataBase.class).build();
        messageDao = imDataBase.messageDao();

        List<MessageListEntity> messageListEntities = new ArrayList<>();

        for(int i = 0;i<10;i++) {
            MessageListEntity bean = new MessageListEntity();
            bean.id = 108465+i;
            bean.user_Id = 123456;
            bean.unReadNum = i;
            messageListEntities.add(bean);
        }

        MessageListEntity[] s = new MessageListEntity[messageListEntities.size()];
        messageListEntities.toArray(s);
        Log.i(TAG, "create: aaaa");

        messageDao.insertMessageList(s);
    }

    @After
    public void close() {
        imDataBase.close();
    }

    @Test
    public void useAppContext() {

        ChatRepository chatRepository = new ChatRepository(messageDao);

        messageDao.getMessageListAll()
        .subscribe(new Consumer<List<MessageListEntity>>() {
            @Override
            public void accept(List<MessageListEntity> messageListEntities) throws Exception {
                assertEquals(10,messageListEntities.size());
            }
        });
        LiveData<List<MessageListEntity>> chatBean = chatRepository.getChatBean(123456);
//        assertEquals(10,chatBean.getValue().size());
        chatBean.observeForever(new Observer<List<MessageListEntity>>() {
            @Override
            public void onChanged(@Nullable List<MessageListEntity> messageListEntities) {
                assertEquals("test",messageListEntities.toString());
            }
        });

//        Context appContext = InstrumentationRegistry.getTargetContext();

//        assertEquals("com.mdshi.component_chat.test", appContext.getPackageName());
    }
}
