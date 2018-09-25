package com.mdshi.common.constan;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.mdshi.common.db.dao.UserDao;
import com.mdshi.common.db.entity.UserEntity;
import com.mdshi.common.utils.SPUtils;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by MaDeng on 2018/9/13.
 */
@Singleton
public class UserData extends LiveData<UserEntity> {


    private Context context;
    private String USERID = "USERID";
    private UserDao dao;

    @Inject
    public UserData(Context context, UserDao dao) {
        this.context = context;
        this.dao = dao;
    }

    @Override
    protected void onInactive() {
        super.onInactive();
    }

    public void logout() {
        SPUtils.getInstance(context).remove(USERID);
        setValue(null);
    }

    @Override
    public void postValue(UserEntity value) {
        SPUtils.getInstance(context).put(USERID,value.userID);
        dao.insertUser(value);
        super.postValue(value);
    }


    @Override
    protected void onActive() {
        Long userId = SPUtils.getInstance(context).getLong(USERID);
        if (userId==-1) {
            setValue(null);
        }else {
            dao.getUserToLiveData(userId).observeForever(this::setValue);
        }
    }
}
