package com.mdshi.im.di.module;

import com.mdshi.component_chat.di.module.FragmentBuildersModule;
import com.mdshi.im.ui.MainActivity;
import com.mdshi.im.ui.NavigationActivity;
import com.mdshi.im.ui.search.SearchContactsActivity;
import com.mdshi.im.ui.setting.SettingActivity;
import com.mdshi.im.ui.userui.EditUserActivity;
import com.mdshi.im.ui.userui.LoginActivity;
import com.mdshi.im.ui.userui.RegisterActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by MaDeng on 2018/9/30.
 */
@Module
public abstract class AppActivityModule {
    @ContributesAndroidInjector()
    public abstract LoginActivity contributeLoginActivity();
    @ContributesAndroidInjector()
    public abstract MainActivity contributeMainActivity();
    @ContributesAndroidInjector(modules = {FragmentBuildersModule.class,AppFragmentModule.class})
    public abstract NavigationActivity contributeNavugationActivity();
    @ContributesAndroidInjector()
    public abstract RegisterActivity contributeRegisterActivity();
    @ContributesAndroidInjector()
    public abstract EditUserActivity contributeEditUserActivity();
    @ContributesAndroidInjector
    public abstract SearchContactsActivity contributeSearchContactsActivity();
    @ContributesAndroidInjector
    public abstract SettingActivity contributeSettingActivity();
}
