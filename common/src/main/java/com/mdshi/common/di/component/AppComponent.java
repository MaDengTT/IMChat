package com.mdshi.common.di.component;

import android.app.Application;

import com.google.gson.Gson;
import com.mdshi.common.di.module.AppModule;
import com.mdshi.common.di.module.ClientModule;
import com.mdshi.common.image.ImageLoader;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Created by MaDeng on 2018/8/31.
 */
@Singleton
@Component(modules = {AppModule.class, ClientModule.class})
public interface AppComponent {
    Application application();
    ImageLoader imageLoader();
    Gson gson();

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }
}
