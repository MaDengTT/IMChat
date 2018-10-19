package com.mdshi.im.di.module;

import com.mdshi.common.di.scope.ActivityScope;
import com.mdshi.im.data.CircleService;
import com.mdshi.im.data.UserService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by MaDeng on 2018/9/13.
 */
@Module
public class AppModule {
    @ActivityScope
    @Provides
    CircleService providesCircleService(Retrofit retrofit) {
        return retrofit.create(CircleService.class);
    }
    @ActivityScope
    @Provides
    UserService providesUserService(Retrofit retrofit) {
        return retrofit.create(UserService.class);
    }
}
