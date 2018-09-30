package com.mdshi.component_chat.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.mdshi.common.db.dao.MessageDao;
import com.mdshi.common.db.entity.MessageEntity;
import com.mdshi.common.db.entity.MessageListEntity;

import org.reactivestreams.Publisher;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

import io.reactivex.android.schedulers.AndroidSchedulers;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by MaDeng on 2018/9/5.
 */
public class ChatRepository {

    MessageDao dao;

    @Inject
    public ChatRepository(MessageDao dao) {
        this.dao = dao;

    }

    public LiveData<List<MessageListEntity>> getChatBean(long userId) {
        LiveData<List<MessageListEntity>> data = dao.getMessageListAll(userId);
        return data;
    }


    public void addMessageList(MessageListEntity entity) {
        Log.d(TAG, "addMessageList: "+entity.toString());
        Flowable.just(entity)
                .map(entity1 -> {dao.insertMessageList(entity1);return true;})
                .subscribeOn(Schedulers.io())
                .doOnError(error-> Log.e(TAG, "addMessageList: ",error ))
                .subscribe();
    }

    private static final String TAG = "ChatRepository";
    public void removeMessageList(MessageListEntity entity) {
        Flowable.just(entity)
                .map(entity1 -> {dao.deleteMessageList(entity1);return true;})
                .subscribeOn(Schedulers.io())
                .doOnError(error-> Log.e(TAG,"removeMessageList：",error))
                .subscribe();
    }

    public void updateMessageList(MessageListEntity entity) {
        Flowable.just(entity)
                .map(entity1 -> {dao.updateMessageList(entity1); return true;})
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public Flowable<List<MessageEntity>> getChatMessageList(long id, int pageSize, int pageNo) {
        return Flowable.just(id)
                .flatMap(aLong -> {
                    List<MessageEntity> data = dao.getMessageById(id, pageSize, pageNo);
                    return Flowable.just(data);
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


    public void addMessage(MessageEntity messageEntity,long userId) {
        MessageListEntity  mle = new MessageListEntity();
        mle.createBean(messageEntity.session_id,userId,messageEntity.other_id,messageEntity.type);
        mle.updateBean(messageEntity.createTime,messageEntity.id,messageEntity.content);
        Log.d(TAG, "addMessage: "+messageEntity.toString());
        dao.insertMessageListAndMessage(mle,messageEntity);
    }

}
