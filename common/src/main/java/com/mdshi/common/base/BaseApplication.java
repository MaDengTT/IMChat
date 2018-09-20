package com.mdshi.common.base;

import android.app.Activity;
import android.app.Application;


import com.mdshi.common.di.component.AppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created by MaDeng on 2018/8/31.
 */
public abstract class BaseApplication extends Application implements HasActivityInjector{
    public abstract  AppComponent appComponent();
//
//    @Inject
//    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

//    @Override
//    public void onCreate() {
//        super.onCreate();
////        AppInjector.init(this);
//    }

//    @Override
//    public AndroidInjector<Activity> activityInjector() {
//        return dispatchingAndroidInjector;
//    }
}
