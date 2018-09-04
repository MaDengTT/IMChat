package com.mdshi.common.di.module;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.google.gson.Gson;
import com.mdshi.common.db.IMDataBase;
import com.mdshi.common.db.dao.MessageDao;
import com.mdshi.common.image.ImageLoader;
import com.mdshi.common.image.glide.GliderLoader;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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

    @Provides
    public IMDataBase provideDataBase(Context context) {
        return Room.databaseBuilder(context, IMDataBase.class,"IMDataBase").build();
    }
}
