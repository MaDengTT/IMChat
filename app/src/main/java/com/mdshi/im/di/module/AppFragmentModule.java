package com.mdshi.im.di.module;

import com.mdshi.im.ui.my.MyFragment;
import com.mdshi.im.ui.show.ShowFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by MaDeng on 2018/10/8.
 */
@Module
public abstract class AppFragmentModule {
    @ContributesAndroidInjector
    public abstract MyFragment contributeMyFragment();
    @ContributesAndroidInjector
    public abstract ShowFragment contributeShowFragment();
}
