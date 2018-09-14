package com.mdshi.common.constan;

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
    public void postValue(UserEntity value) {
        super.postValue(value);
    }

    @Override
    public void setValue(UserEntity value) {
        super.setValue(value);
    }

}
