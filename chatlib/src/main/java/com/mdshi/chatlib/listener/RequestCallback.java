package com.mdshi.chatlib.listener;

/**
 * Created by MaDeng on 2018/11/19.
 */
public interface RequestCallback<T> {
    void onSuccess(T param);

    void onFailde(int code);

    void onException(Throwable exception);
}
