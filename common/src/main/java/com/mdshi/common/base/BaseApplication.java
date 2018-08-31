package com.mdshi.common.base;

import android.app.Application;

import com.mdshi.common.di.component.AppComponent;

/**
 * Created by MaDeng on 2018/8/31.
 */
public abstract class BaseApplication extends Application{
    public abstract AppComponent AppComponent();
}
