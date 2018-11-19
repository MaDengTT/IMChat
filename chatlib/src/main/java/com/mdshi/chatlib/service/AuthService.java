package com.mdshi.chatlib.service;

import android.text.TextUtils;

import com.mdshi.chatlib.Bean.UserInfo;
import com.mdshi.chatlib.connection.BaseConnection;
import com.mdshi.chatlib.listener.AbortableFuture;
import com.mdshi.chatlib.listener.BaseListener;
import com.mdshi.chatlib.listener.RequestCallback;

/**
 * Created by MaDeng on 2018/11/19.
 */
public class AuthService extends IMService{

    private UserInfo info;

    public AuthService(BaseConnection connection) {
        super(connection);
    }

    public UserInfo getInfo() {
        return info;
    }

    public AbortableFuture<UserInfo> login(final UserInfo user) {
        final Future<UserInfo> userInfoFuture = new Future<>();
        getConnection().subListener(new BaseListener() {
            @Override
            public void onSuccess() {
                info = user;
                userInfoFuture.postData(user);
            }

            @Override
            public void onFailure(Throwable value) {
                userInfoFuture.postException(0,value);
            }
        });
        if (!TextUtils.isEmpty(user.toKen)) {
            getConnection().sub(user.toKen);
        }else {
            getConnection().sub(user.account);
        }
        return userInfoFuture;
    }

    public AbortableFuture<UserInfo> logOut() {
        final Future<UserInfo> userInfoFuture = new Future<>();
        getConnection().subListener(new BaseListener() {
            @Override
            public void onSuccess() {
                userInfoFuture.postData(info);
            }

            @Override
            public void onFailure(Throwable value) {
                userInfoFuture.postException(0,value);
            }
        });
        return userInfoFuture;
    }

}
