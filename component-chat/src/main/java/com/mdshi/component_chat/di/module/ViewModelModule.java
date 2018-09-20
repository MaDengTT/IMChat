package com.mdshi.component_chat.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.mdshi.common.di.scope.ViewModelKey;
import com.mdshi.common.viewmodel.ViewModelFactory;
import com.mdshi.component_chat.ui.ChatModel;
import com.mdshi.component_chat.ui.chat.ChatActivityModel;
import com.mdshi.component_chat.ui.contacts.ContactsModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by MaDeng on 2018/9/19.
 */
@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ContactsModel.class)
    public abstract ViewModel bindContactsModel(ContactsModel contactsModel);

    @Binds
    @IntoMap
    @ViewModelKey(ChatModel.class)
    public abstract ViewModel bindChatModel(ChatModel model);
    @Binds
    @IntoMap
    @ViewModelKey(ChatActivityModel.class)
    public abstract ViewModel bindChatActivityModel(ChatActivityModel model);

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

}
