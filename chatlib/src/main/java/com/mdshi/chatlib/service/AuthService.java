package com.mdshi.chatlib.service;

import android.text.TextUtils;

import com.mdshi.chatlib.Bean.UserInfo;
import com.mdshi.chatlib.connection.BaseConnection;
import com.mdshi.chatlib.listener.AdoptableFuture;
import com.mdshi.chatlib.listener.Promise;
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

    public AdoptableFuture<UserInfo> login(final UserInfo user) {
        final Promise<UserInfo> userInfoFuture = new Promise<>();
        RequestCallback<String> callback = new RequestCallback<String>() {
            @Override
            public void onSuccess(String param) {
                userInfoFuture.onSuccess(user);
            }

            @Override
            public void onFailed(int code) {
                userInfoFuture.onFailed(code);
            }

            @Override
            public void onException(Throwable exception) {
                userInfoFuture.onException(exception);
            }
        };
        if (!TextUtils.isEmpty(user.toKen)) {
            getConnection().sub(user.toKen,callback);
        }else {
            getConnection().sub(user.account,callback);
        }
        return userInfoFuture;
    }

    public AdoptableFuture<String> logOut() {
        final Promise<String> userInfoFuture = new Promise<>();
        connection.unSub(userInfoFuture);
        return userInfoFuture;
    }

}
