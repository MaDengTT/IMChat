package com.mdshi.im.ui.search;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import com.mdshi.common.db.entity.UserEntity;
import com.mdshi.im.data.UserService;

import javax.inject.Inject;

/**
 * Created by MaDeng on 2018/10/12.
 */
public class SearchUserDataSource extends PageKeyedDataSource<Integer,UserEntity> {


    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, UserEntity> callback) {

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, UserEntity> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, UserEntity> callback) {

    }
}
