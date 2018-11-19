package com.mdshi.chatlib.listener;

/**
 * Created by MaDeng on 2018/11/19.
 */
public interface InvocationFuture<T> {
    void setCallback(RequestCallback<T> callback);
}
