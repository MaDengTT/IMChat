package com.mdshi.component_chat.di.module;

import com.mdshi.component_chat.ui.ChatFragment;
import com.mdshi.component_chat.ui.contacts.ContactsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by MaDeng on 2018/9/20.
 */
@Module
public abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    public abstract ChatFragment contributeChatFragment();

    @ContributesAndroidInjector
    public abstract ContactsFragment contributeContactsFragment();
}
