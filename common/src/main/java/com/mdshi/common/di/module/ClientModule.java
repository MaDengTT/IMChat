package com.mdshi.common.di.module;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.google.gson.Gson;
import com.mdshi.common.constan.UserData;
import com.mdshi.common.db.IMDataBase;
import com.mdshi.common.db.dao.ContactsDao;
import com.mdshi.common.db.dao.MessageDao;
import com.mdshi.common.db.dao.UserDao;
import com.mdshi.common.db.entity.UserEntity;
import com.mdshi.common.image.ImageLoader;
import com.mdshi.common.image.glide.GliderLoader;
import com.mdshi.common.net.RetrofitClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by MaDeng on 2018/8/31.
 */
@Module
public class ClientModule {

    @Provides
    public static ImageLoader provides(){
        return new GliderLoader();
    }

    @Provides
    public Gson provideGson() {
        return new Gson();
    }

    @Singleton
    @Provides
    public MessageDao provideMessageDao(IMDataBase dataBase) {
        return dataBase.messageDao();
    }
    @Singleton
    @Provides
    public ContactsDao provideContactsDao(IMDataBase dataBase) {
        return dataBase.contactsDao();
    }

    @Singleton
    @Provides
    public UserDao provideUserDao(IMDataBase dataBase){
        return dataBase.userDao();
    }
    @Singleton
    @Provides
    public IMDataBase provideDataBase(Context context) {
        return Room.databaseBuilder(context, IMDataBase.class,"IMDataBase").build();
    }

    @Singleton
    @Provides
    public Retrofit provideRetrofit(Context context) {
        return RetrofitClient.createService(context,"http://www.mdshi.cn:8081");
    }

    @Singleton
    @Provides
    public UserData provideUserData() {
        return new UserData();
    }
}
