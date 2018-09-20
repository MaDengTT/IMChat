package com.mdshi.component_chat.di.module;

import com.mdshi.component_chat.ui.ChatActivity;
import com.mdshi.component_chat.ui.MainChatActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by MaDeng on 2018/9/20.
 */
@Module
public abstract class ActivityModule {
    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    public abstract MainChatActivity contributeMainActivity();

    @ContributesAndroidInjector
    public abstract ChatActivity contributeChatActivity();
}
