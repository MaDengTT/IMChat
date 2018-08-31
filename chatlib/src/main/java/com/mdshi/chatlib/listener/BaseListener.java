package com.mdshi.chatlib.listener;

/**
 * Created by MaDeng on 2018/8/31.
 */
public interface BaseListener  {
    public void onSuccess();
    public void onFailure(Throwable value);
}
