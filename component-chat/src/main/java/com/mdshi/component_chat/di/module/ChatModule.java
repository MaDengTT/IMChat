package com.mdshi.component_chat.di.module;

import com.mdshi.common.db.dao.MessageDao;
import com.mdshi.component_chat.data.ChatRepository;
import com.mdshi.component_chat.ui.ChatModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by MaDeng on 2018/9/5.
 */
@Module
public class ChatModule {

    @Provides
    public static ChatRepository provideRepository(MessageDao dao) {
        return new ChatRepository(dao);
    }

}
