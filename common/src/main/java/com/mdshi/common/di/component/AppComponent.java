package com.mdshi.common.di.component;

import android.app.Application;

import com.google.gson.Gson;
import com.mdshi.common.constan.UserData;
import com.mdshi.common.db.dao.CircleDao;
import com.mdshi.common.db.dao.ContactsDao;
import com.mdshi.common.db.dao.MessageDao;
import com.mdshi.common.db.dao.MessageListDao;
import com.mdshi.common.db.dao.UserDao;
import com.mdshi.common.di.module.AppModule;
import com.mdshi.common.di.module.ClientModule;
import com.mdshi.common.image.ImageLoader;
import com.mdshi.common.vo.AppExecutors;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import retrofit2.Retrofit;

/**
 * Created by MaDeng on 2018/8/31.
 */
@Singleton
@Component(modules = {AppModule.class, ClientModule.class,})
public interface AppComponent {

//    void inject(BaseApplication application);

    Application application();
    ImageLoader imageLoader();
    Gson gson();

    MessageDao messageDao();
    ContactsDao contactsDao();
    UserDao userDao();

    Retrofit retrofit();

    UserData userData();

    AppExecutors appExecutors();

    CircleDao circleDao();

    MessageListDao listDao();

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }
}
