package com.mdshi.chatlib.service;

import android.text.TextUtils;

import com.mdshi.chatlib.Bean.UserInfo;
import com.mdshi.chatlib.connection.BaseConnection;
import com.mdshi.chatlib.listener.AdoptableFuture;
import com.mdshi.chatlib.listener.Function;
import com.mdshi.chatlib.listener.Promise;
import com.mdshi.chatlib.listener.RequestCallback;
import com.mdshi.chatlib.listener.Transformations;

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

    public boolean isLogin() {
        return info!=null;
    }

    public UserInfo currentUser() {
        return info;
    }

    public AdoptableFuture<UserInfo> login(final UserInfo user) {
        final Promise<UserInfo> userInfoFuture = new Promise<>();
        RequestCallback<String> callback = Transformations.map(userInfoFuture, new Function<String, UserInfo>() {
            @Override
            public UserInfo apply(String s) {
                return user;
            }
        });
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
        info = null;
        return userInfoFuture;
    }

}
