package com.mdshi.im;

import android.app.Activity;
import android.app.Application;

import com.mdshi.chatlib.IMChat;
import com.mdshi.common.base.BaseApplication;
import com.mdshi.common.di.component.AppComponent;
import com.mdshi.common.di.component.DaggerAppComponent;

import dagger.android.AndroidInjector;

/**
 * Created by MaDeng on 2018/9/3.
 */
public class MyApplication extends BaseApplication {

    private AppComponent appComponent;
    private String IMKEY="";

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder().application(this).build();
//        ininIMChat();
    }

    private void ininIMChat() {
        IMChat.init(IMKEY);
    }

    @Override
    public AppComponent appComponent() {
        return appComponent;
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return null;
    }
}
