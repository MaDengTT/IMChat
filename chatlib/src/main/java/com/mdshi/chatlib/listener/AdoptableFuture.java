package com.mdshi.chatlib.listener;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by MaDeng on 2018/11/19.
 */
public interface AdoptableFuture<T> extends InvocationFuture<T>{
    T await(long amount, TimeUnit unit) throws Exception;

    T await() throws Exception;
}
