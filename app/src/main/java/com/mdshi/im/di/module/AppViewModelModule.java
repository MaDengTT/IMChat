package com.mdshi.im.di.module;

import android.arch.lifecycle.ViewModel;

import com.mdshi.common.di.scope.ViewModelKey;
import com.mdshi.component_chat.ui.contacts.ContactsModel;
import com.mdshi.im.ui.userui.UserModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by MaDeng on 2018/9/30.
 */
@Module
public abstract class AppViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(UserModel.class)
    public abstract ViewModel bindContactsModel(UserModel userModel);

}
