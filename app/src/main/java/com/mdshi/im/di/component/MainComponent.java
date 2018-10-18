package com.mdshi.im.di.component;

import com.mdshi.common.constan.UserData;
import com.mdshi.common.di.component.AppComponent;
import com.mdshi.common.di.scope.ActivityScope;
import com.mdshi.im.di.module.AppModule;
import com.mdshi.im.ui.userui.UserModel;

import dagger.Component;

/**
 * Created by MaDeng on 2018/9/13.
 */
@ActivityScope
@Component(dependencies = AppComponent.class,modules = {AppModule.class})
public interface MainComponent {
//    void inject(LoginActivity activity);
//    void inject(RegisterActivity activity);
//
//    void inject(MainActivity mainActivity);

    UserData userData();

    UserModel userModel();
}
