package com.mdshi.component_chat.di.component;

import com.mdshi.common.di.component.AppComponent;
import com.mdshi.common.di.scope.ActivityScope;
import com.mdshi.component_chat.MyApplication;
import com.mdshi.component_chat.di.module.ActivityModule;
import com.mdshi.component_chat.di.module.ChatModule;
import com.mdshi.component_chat.di.module.ViewModelModule;
import com.mdshi.component_chat.service.IMChatService;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by MaDeng on 2018/9/5.
 */
@ActivityScope
@Component(dependencies = AppComponent.class,modules = {
        AndroidInjectionModule.class,
        AndroidSupportInjectionModule.class,
        ChatModule.class,
        ViewModelModule.class,
        ActivityModule.class,
})
public interface ChatComponent {

    void inject(MyApplication application);

    void inject(IMChatService service);
//    ChatModel chatModel();
//    ChatActivityModel chatActivityModel();
}
