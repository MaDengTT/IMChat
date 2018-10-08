package com.mdshi.im;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.mdshi.chatlib.Debug;
import com.mdshi.chatlib.IMChat;
import com.mdshi.common.base.BaseApplication;
import com.mdshi.common.constan.UserData;
import com.mdshi.common.db.entity.UserEntity;
import com.mdshi.common.di.component.AppComponent;
import com.mdshi.common.di.component.DaggerAppComponent;
import com.mdshi.component_chat.service.IMChatService;
import com.mdshi.im.di.AppInjector;

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

    @Inject
    UserData userData;
    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder().application(this).build();
        AppInjector.init(this);
        initIMChat();
    }

    private void initIMChat() {
        userData.observeForever(userEntity -> {
            if (userEntity == null||userEntity.userID==0) {
                IMChat.unConnect();
            }else {
                Debug.init(true);
                IMChat.init(String.valueOf(userEntity.userID));
                IMChat.connect();
                startService(new Intent(getApplicationContext(),IMChatService.class));
            }
        });
    }

    @Override
    public AppComponent appComponent() {
        return appComponent;
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
