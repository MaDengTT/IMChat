package com.mdshi.component_chat.di.module;

import android.arch.persistence.room.PrimaryKey;

import com.mdshi.common.db.dao.ContactsDao;
import com.mdshi.common.db.dao.MessageDao;
import com.mdshi.common.di.scope.ActivityScope;
import com.mdshi.component_chat.adapter.ContactsAdapter;
import com.mdshi.component_chat.data.ChatRepository;
import com.mdshi.component_chat.data.ContactsRepository;
import com.mdshi.component_chat.data.ContactsService;
import com.mdshi.component_chat.ui.ChatModel;
import com.mdshi.component_chat.ui.chat.ChatActivityModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import retrofit2.Retrofit;

/**
 * Created by MaDeng on 2018/9/5.
 */

@Module
public class ChatModule {

    @ActivityScope
    @Provides
    public static ContactsService provideContactsService(Retrofit retrofit) {
        return retrofit.create(ContactsService.class);
    }

}
