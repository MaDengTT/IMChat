package com.mdshi.im.di.module;

import android.arch.lifecycle.ViewModel;

import com.mdshi.common.di.scope.ViewModelKey;
import com.mdshi.im.ui.search.SearchModel;
import com.mdshi.im.ui.show.ShowViewModel;
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
    @Binds
    @IntoMap
    @ViewModelKey(SearchModel.class)
    public abstract ViewModel bindSearchModel(SearchModel serachModel);

    @Binds
    @IntoMap
    @ViewModelKey(ShowViewModel.class)
    public abstract ViewModel bindShowModel(ShowViewModel showViewModel);
}
