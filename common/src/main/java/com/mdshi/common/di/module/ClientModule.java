package com.mdshi.common.di.module;

import com.google.gson.Gson;
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

}
