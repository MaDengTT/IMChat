package com.mdshi.component_chat.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.gson.Gson;
import com.mdshi.chatlib.Bean.MessageBean;
import com.mdshi.chatlib.IMChat;
import com.mdshi.chatlib.listener.MessageListener;
import com.mdshi.common.base.BaseApplication;
import com.mdshi.common.constan.UserData;
import com.mdshi.common.db.dao.MessageDao;
import com.mdshi.common.db.entity.MessageEntity;
import com.mdshi.common.db.entity.MessageListEntity;
import com.mdshi.component_chat.ChatManager;
import com.mdshi.component_chat.bean.ChatBean;
import com.mdshi.component_chat.di.component.DaggerChatComponent;
import com.mdshi.component_chat.utils.BeanUtils;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class IMChatService extends Service {

    public IMChatService() {
    }

    @Inject
    MessageDao dao;

    @Inject
    Gson gson;

    @Inject
    UserData userdata;
    private static final String TAG = "IMChatService";
    @Override
    public void onCreate() {
        super.onCreate();

        DaggerChatComponent.builder().appComponent(((BaseApplication)getApplication()).appComponent()).build().inject(this);

        IMChat.addMessageListener(new MessageListener() {
            @Override
            public void message(String data) {
                Log.d(TAG, "message: "+data);

            }

            @Override
            public void messagetbean(MessageBean bean) {
                if (MessageBean.R_CHAT_KEY.equals(bean.key)) {

                    Flowable.just(bean.message)
                            .map(s -> gson.fromJson(s, MessageEntity.class))
                            .flatMap(s1->addMegToDB(s1))
                            .map(s1->BeanUtils.MsgToChatBean(s1,userdata.getValue().userID))
                            .subscribe(new Subscriber<ChatBean>() {
                                @Override
                                public void onSubscribe(Subscription s) {

                                }

                                @Override
                                public void onNext(ChatBean bean) {
                                    ChatManager.getIns().receive(bean);
                                }

                                @Override
                                public void onError(Throwable t) {
                                    Log.e(TAG, "onError: ", t);
                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }
            }

            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(Throwable value) {

            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private Flowable<MessageEntity> addMegToDB(MessageEntity messageEntity) {
        return Flowable.just(messageEntity)
                .flatMap(aLong -> {
                    MessageListEntity msgListBySessionID = dao.getMsgListBySessionID(aLong.session_id);
                    if (msgListBySessionID == null) {
                        msgListBySessionID = new MessageListEntity();
                    }
                    msgListBySessionID.updateBean(aLong.createTime,aLong.id,aLong.content);
                    if (msgListBySessionID.id == 0) {
                        msgListBySessionID.id = aLong.session_id;
                        msgListBySessionID.user_Id = messageEntity.tUserId;
                        dao.insertMessageListAndMessage(msgListBySessionID,aLong);
                    }else {
                        dao.insertMessage(aLong);
                        dao.updateMessageList(msgListBySessionID);
                    }
                    return Flowable.just(aLong);
                    })
                .subscribeOn(Schedulers.io()).map(s1->
                {ChatBean bean = BeanUtils.MsgToChatBean(s1,userdata.getValue().userID);
                ChatManager.getIns().receive(bean);
                return s1;});
    }
}
