package com.mdshi.component_chat.di.component;

import com.mdshi.common.di.component.AppComponent;
import com.mdshi.common.di.scope.ActivityScope;
import com.mdshi.component_chat.di.module.ActivityModule;
import com.mdshi.component_chat.di.module.ChatModule;
import com.mdshi.component_chat.di.module.ViewModelModule;
import com.mdshi.component_chat.service.IMChatService;
import com.mdshi.component_chat.ui.MainChatActivity;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by MaDeng on 2018/9/5.
 */
@ActivityScope
@Component(dependencies = AppComponent.class,modules = {
        AndroidSupportInjectionModule.class,
        ChatModule.class,
        ViewModelModule.class,
        ActivityModule.class,
})
public interface ChatComponent {
    void inject(IMChatService service);

    void inject(MainChatActivity activity);

//    ChatModel chatModel();
//    ChatActivityModel chatActivityModel();
}
