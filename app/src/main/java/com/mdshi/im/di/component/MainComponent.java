package com.mdshi.im.di.component;

import com.mdshi.common.di.component.AppComponent;
import com.mdshi.common.di.scope.ActivityScope;
import com.mdshi.im.di.module.UserModule;
import com.mdshi.im.ui.userui.LoginActivity;
import com.mdshi.im.ui.userui.UserModel;

import dagger.Component;

/**
 * Created by MaDeng on 2018/9/13.
 */
@ActivityScope
@Component(dependencies = AppComponent.class,modules = {UserModule.class})
public interface MainComponent {
    void inject(LoginActivity activity);

    UserModel userModel();
}
