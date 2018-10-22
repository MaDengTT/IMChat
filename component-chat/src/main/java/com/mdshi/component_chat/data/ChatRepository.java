package com.mdshi.component_chat.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.text.TextUtils;
import android.util.Log;

import com.mdshi.common.db.bean.MessageListBean;
import com.mdshi.common.db.bean.UserInfo;
import com.mdshi.common.db.dao.ContactsDao;
import com.mdshi.common.db.dao.MessageDao;
import com.mdshi.common.db.dao.MessageListDao;
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
    private MessageListDao listDao;
    @Inject
    public ChatRepository(MessageDao dao,ContactsDao contactsDao,MessageListDao listDao) {
        this.dao = dao;
        this.contactsDao = contactsDao;
        this.listDao = listDao;
    }

    public LiveData<List<MessageListBean>> getChatBean(long userId) {
        LiveData<List<MessageListBean>> data = listDao.getAll(userId);
//        Flowable.just(0)
//                .map(new Function<Integer, Integer>() {
//                    @Override
//                    public Integer apply(Integer integer) throws Exception {
//                        List<MessageListBean> allTest = listDao.getAllTest(userId);
//                        allTest.size();
//                        return null;
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//        .subscribe();
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

//
//    public void addMessage(MessageEntity messageEntity,long userId) {
//        MessageListEntity  mle = new MessageListEntity();
//        mle.id = messageEntity.session_id;
//        mle.user_Id = userId;
//        mle.unReadNum++;
//        mle.contactsId = messageEntity.tUserId;
//        dao.insertMessageListAndMessage(mle,messageEntity);
//    }

    public LiveData<ContactsEntity> getContact(long user, long contactsId) {
        return contactsDao.findContactsToLiveData(user,contactsId);
    }

    public void chatMessageToDb(MessageEntity messageEntity, long userId, long contactsId) {
        MessageListEntity mle = dao.getMsgListBySessionID(messageEntity.session_id);

        if (userId != contactsId) {
            ContactsEntity contacts = contactsDao.findContacts(userId, contactsId);
            messageEntity.userInfo = new UserInfo();
            messageEntity.userInfo.userName = TextUtils.isEmpty(contacts.contactsName) ? contacts.info.userName : contacts.contactsName;
            messageEntity.userInfo.avatar = contacts.info.avatar;
            messageEntity.userInfo.userId = contactsId;
            messageEntity.userInfo.phone = contacts.info.phone;
            messageEntity.userInfo.email = contacts.info.email;
        }else {

        }
        if (mle == null) {
            mle = new MessageListEntity();
            mle.id = messageEntity.session_id;
            mle.user_Id = userId;
            mle.contactsId = contactsId;
        }
        mle.unReadNum++;
        dao.insertMessageListAndMessage(mle, messageEntity);
    }
}
