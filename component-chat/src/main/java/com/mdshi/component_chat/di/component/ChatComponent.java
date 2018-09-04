package com.mdshi.component_chat.di.component;

import com.mdshi.common.di.component.AppComponent;
import com.mdshi.common.di.scope.ActivityScope;
import com.mdshi.component_chat.di.module.ChatModule;
import com.mdshi.component_chat.ui.chatlist.ChatFragment;
import com.mdshi.component_chat.ui.chatlist.ChatListContract;

import dagger.Component;

/**
 * Created by MaDeng on 2018/9/4.
 */
@ActivityScope
@Component(dependencies = AppComponent.class,modules = {ChatModule.class})
public interface ChatComponent {
    void inject(ChatFragment fragment);

    ChatListContract.Presenter providePresenter();
}
