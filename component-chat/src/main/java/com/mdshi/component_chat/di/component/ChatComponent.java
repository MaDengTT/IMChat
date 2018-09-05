package com.mdshi.component_chat.di.component;

import com.mdshi.common.di.component.AppComponent;
import com.mdshi.common.di.scope.ActivityScope;
import com.mdshi.component_chat.di.module.ChatModule;
import com.mdshi.component_chat.ui.ChatFragment;
import com.mdshi.component_chat.ui.ChatModel;

import javax.inject.Inject;

import dagger.Component;

/**
 * Created by MaDeng on 2018/9/5.
 */
@ActivityScope
@Component(dependencies = AppComponent.class,modules = {ChatModule.class})
public interface ChatComponent {

    void inject(ChatFragment fragment);
    ChatModel chatModel();

}
