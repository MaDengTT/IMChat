package com.mdshi.chatlib.listener;

/**
 * Created by MaDeng on 2018/11/21.
 */
public interface Function<T,R> {
    R apply(T t);
}
