package com.mdshi.common.di.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by MaDeng on 2018/8/31.
 */
@Module
public class AppModule {
    Context context;

    @Provides
    @Singleton
    Context provideContext(Application application){
        return application;
    }
}
