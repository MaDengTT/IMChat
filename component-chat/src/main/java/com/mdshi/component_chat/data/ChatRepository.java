package com.mdshi.component_chat.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.text.TextUtils;
import android.util.Log;

import com.mdshi.common.db.dao.ContactsDao;
import com.mdshi.common.db.dao.MessageDao;
import com.mdshi.common.db.dao.UserDao;
import com.mdshi.common.db.entity.ContactsEntity;
import com.mdshi.common.db.entity.MessageEntity;
import com.mdshi.common.db.entity.MessageListEntity;

import org.reactivestreams.Publisher;

import java.util.Date;
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

    private MessageDao dao;
    private ContactsDao contactsDao;
    @Inject
    public ChatRepository(MessageDao dao,ContactsDao contactsDao) {
        this.dao = dao;
        this.contactsDao = contactsDao;
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
        ContactsEntity contacts = contactsDao.findContacts(userId,messageEntity.other_id);
        mle.createBean(messageEntity.session_id,
                userId,
                messageEntity.other_id,
                messageEntity.type,
                TextUtils.isEmpty(contacts.contactsName)?contacts.info.userName:contacts.contactsName);
        mle.updateBean(new Date(messageEntity.createTime),messageEntity.id,messageEntity.content);
        if (contacts != null) {
            mle.setUserInfo(contacts.contactsName,contacts.info.avatar);
        }
        Log.d(TAG, "addMessage: "+messageEntity.toString());
        messageEntity.id = 0;
        dao.insertMessageListAndMessage(mle,messageEntity);
    }

    public LiveData<ContactsEntity> getContact(long user, long contactsId) {
        return contactsDao.findContactsToLiveData(user,contactsId);
    }
}
