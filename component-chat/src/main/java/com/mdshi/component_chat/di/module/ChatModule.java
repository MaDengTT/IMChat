package com.mdshi.component_chat.di.module;

import com.mdshi.component_chat.ui.chatlist.ChatListContract;
import com.mdshi.component_chat.ui.chatlist.ChatListModel;
import com.mdshi.component_chat.ui.chatlist.ChatListPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by MaDeng on 2018/9/4.
 */
@Module
public class ChatModule {

    private ChatListContract.View view;

    public void setView(ChatListContract.View view) {
        this.view = view;
    }

    @Provides
    ChatListContract.View provideChatView() {
        return view;
    }

    @Provides
    ChatListContract.Model provideChatModel(ChatListModel model){
        return model;
    }
    @Provides
    ChatListContract.Presenter provideChatPresenter(ChatListPresenter presenter){
        return presenter;
    }

}
