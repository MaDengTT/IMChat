package com.mdshi.im.data;

import android.arch.lifecycle.LiveData;

import com.mdshi.common.db.entity.UserEntity;

/**
 * Created by MaDeng on 2018/9/13.
 */
public class UserData extends LiveData<UserEntity> {

    @Override
    protected void onInactive() {
        super.onInactive();
    }

    @Override
    protected void postValue(UserEntity value) {
        super.postValue(value);
    }

    @Override
    protected void setValue(UserEntity value) {
        super.setValue(value);
    }

}
