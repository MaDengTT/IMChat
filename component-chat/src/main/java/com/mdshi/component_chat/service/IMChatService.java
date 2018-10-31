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
import com.mdshi.common.db.entity.MessageEntity;
import com.mdshi.common.rx.RxUtils;
import com.mdshi.component_chat.ChatManager;
import com.mdshi.component_chat.bean.ChatBean;
import com.mdshi.component_chat.data.ChatRepository;
import com.mdshi.component_chat.di.component.DaggerChatComponent;
import com.mdshi.component_chat.utils.BeanUtils;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;


import javax.inject.Inject;

import io.reactivex.Flowable;

public class IMChatService extends Service {

    public IMChatService() {
    }

    @Inject
    Gson gson;

    @Inject
    UserData userdata;

    @Inject
    ChatRepository repository;

    long userid;

    private static final String TAG = "IMChatService";
    @Override
    public void onCreate() {
        super.onCreate();

        DaggerChatComponent.builder().appComponent(((BaseApplication)getApplication()).appComponent()).build().inject(this);

        userdata.observeForever(userEntity -> {
            if (userEntity != null) {
                userid = userEntity.userId;
                initImChat();
            }
        });


    }

    private void initImChat() {
        IMChat.addMessageListener(new MessageListener() {
            @Override
            public void message(String data) {
                Log.d(TAG, "message: "+data);
            }

            @Override
            public void messageTBean(MessageBean bean) {
                if (MessageBean.R_CHAT_KEY.equals(bean.key)) {
                    Flowable.just(bean.message)
                            .map(s -> {
                                MessageEntity messageEntity = gson.fromJson(s, MessageEntity.class);
                                return messageEntity;
                            })
                            .map(s1->{
//                                MessageBean bean1 = new MessageBean(MessageBean.D_CHAT_KEY,"{\"id\":"+s1.id+"}");
//                                IMChat.sendMessage(bean1,null);
                                return s1;
                            })
                            .map(messageEntity -> {
                                repository.chatMessageToDb(messageEntity,userid,messageEntity.fUserId);
                                ChatManager.getIns().receive(BeanUtils.MsgToChatBean(messageEntity,userid));
                                return messageEntity;
                            }).map(s1-> BeanUtils.MsgToChatBean(s1,userid))
                            .compose(RxUtils.switchMainThread())
                            .subscribe(new Subscriber<ChatBean>() {
                                @Override
                                public void onSubscribe(Subscription s) {

                                }

                                @Override
                                public void onNext(ChatBean bean) {
//                                    ChatManager.getIns().receive(bean);
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
    public void onDestroy() {
        startService(new Intent(this, IMChatService.class));
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
