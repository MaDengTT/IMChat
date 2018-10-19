package com.mdshi.im.di.component;


import com.mdshi.common.di.scope.ActivityScope;
import com.mdshi.component_chat.di.module.ActivityModule;
import com.mdshi.component_chat.di.module.ViewModelModule;
import com.mdshi.im.MyApplication;
import com.mdshi.im.data.CircleService;
import com.mdshi.im.data.UserService;
import com.mdshi.im.di.module.AppActivityModule;
import com.mdshi.im.di.module.AppModule;
import com.mdshi.im.di.module.AppViewModelModule;


import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by MaDeng on 2018/9/30.
 */
@ActivityScope
@Component(dependencies = com.mdshi.common.di.component.AppComponent.class,modules = {
        AndroidSupportInjectionModule.class,
        ViewModelModule.class,
        ActivityModule.class,
        AppActivityModule.class,
        AppViewModelModule.class,
        AppModule.class
})
public interface AppMainComponent {
    void inject(MyApplication myApplication);

    CircleService circleService();
    UserService userService();
}
