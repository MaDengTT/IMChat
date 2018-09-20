package com.mdshi.component_chat;

//import com.mdshi.chatlib.IMChat;
import android.app.Activity;

import com.mdshi.common.base.BaseApplication;
import com.mdshi.common.di.component.AppComponent;
import com.mdshi.common.di.component.DaggerAppComponent;
import com.mdshi.component_chat.bean.ChatBean;
import com.mdshi.component_chat.data.ChatRepository;
import com.mdshi.component_chat.di.AppInjector;
import com.mdshi.component_chat.listener.ChatListener;
import com.mdshi.component_chat.ui.ChatActivity;
import com.mdshi.component_chat.ui.chat.ChatActivityModel;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;

/**
 * Created by MaDeng on 2018/9/3.
 */
public class MyApplication extends BaseApplication {

    private AppComponent appComponent;
    private String IMKEY="";

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
//        IMChat.init(IMKEY);
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
