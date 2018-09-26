package com.mdshi.component_chat;


import android.app.Activity;
import android.util.Log;

import com.mdshi.chatlib.Bean.MessageBean;
import com.mdshi.chatlib.Debug;
import com.mdshi.chatlib.IMChat;
import com.mdshi.chatlib.listener.MessageListener;
import com.mdshi.common.base.BaseApplication;
import com.mdshi.common.di.component.AppComponent;
import com.mdshi.common.di.component.DaggerAppComponent;
import com.mdshi.component_chat.bean.ChatBean;
import com.mdshi.component_chat.data.ChatRepository;
import com.mdshi.component_chat.di.AppInjector;
import com.mdshi.component_chat.listener.ChatListener;
import com.mdshi.component_chat.ui.chat.ChatActivityModel;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;

/**
 * Created by MaDeng on 2018/9/3.
 */
public class MyApplication extends BaseApplication {

    private AppComponent appComponent;
    private String IMKEY="123456";

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder().application(this).build();
        AppInjector.init(this);
        ininIMChat();
    }

    private void ininIMChat() {
        //TODO TEST Code
        ChatManager.getIns().registerChatListener(new ChatListener() {
            @Override
            public boolean callback(ChatBean bean) {
                ChatActivityModel a = new ChatActivityModel(new ChatRepository(appComponent.messageDao()));
                a.addMsgChatData(bean);
                return false;
            }
        });
        Debug.init(true);
        IMChat.init(IMKEY);
        IMChat.connect();
        IMChat.addMessageListener(new MessageListener() {
            @Override
            public void message(String data) {
                Log.d("IMChat", "message: "+data);
            }

            @Override
            public void messageTbean(MessageBean bean) {

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
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public AppComponent appComponent() {
        return appComponent;
    }
}
