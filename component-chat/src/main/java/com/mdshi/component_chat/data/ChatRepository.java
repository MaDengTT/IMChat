package com.mdshi.component_chat.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import com.mdshi.common.db.dao.MessageDao;
import com.mdshi.common.db.entity.MessageListEntity;
import com.mdshi.component_chat.bean.ChatBean;

import java.util.Date;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by MaDeng on 2018/9/5.
 */
public class ChatRepository {

    MessageDao dao;

    public ChatRepository(MessageDao dao) {
        this.dao = dao;
    }

    public LiveData<List<MessageListEntity>> getChatBean(long userId) {
        LiveData<List<MessageListEntity>> data = new MutableLiveData<>();

        data = dao.getMessageListAll(userId);

        return data;
    }

    public void addMessageList(MessageListEntity entity) {
        Flowable.just(entity)
                .observeOn(Schedulers.io())
                .subscribe(entity1 -> dao.insertMessageList(entity));
    }
}
