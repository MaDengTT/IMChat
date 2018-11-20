package com.mdshi.chatlib.listener;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.security.auth.callback.Callback;

/**
 * Created by MaDeng on 2018/11/20.
 */
public class Promise<T> implements RequestCallback<T>,AdoptableFuture<T> {

    private final CountDownLatch latch = new CountDownLatch(1);
    private RequestCallback<T> next;
    private Throwable error;
    private int failedCode = 0;
    private T value;

    @Override
    public void then(RequestCallback<T> callback) {
        boolean fire = false;
        synchronized (this) {
            next = callback;
            if (latch.getCount() == 0) {
                fire = true;
            }
        }
        if (fire) {
            if (error != null) {
                callback.onException(error);
            } else if (failedCode != 0) {
                callback.onFailed(failedCode);
            }else {
                callback.onSuccess(value);
            }
        }
    }

    @Override
    public void onSuccess(T param) {
        RequestCallback<T> callback = null;
        synchronized (this) {
            this.value = param;
            latch.countDown();
            callback = next;
        }

        if (callback != null) {
            callback.onSuccess(param);
        }
    }

    @Override
    public void onFailed(int code) {
        RequestCallback<T> callback = null;
        synchronized (this) {
            this.failedCode = code;
            latch.countDown();
            callback = next;
        }

        if (callback != null) {
            callback.onFailed(code);
        }
    }

    @Override
    public void onException(Throwable exception) {
        RequestCallback<T> callback = null;
        synchronized (this) {
            this.error = exception;
            latch.countDown();
            callback = next;
        }

        if (callback != null) {
            callback.onException(exception);
        }
    }

    @Override
    public T await(long amount, TimeUnit unit) throws Exception {
        if( latch.await(amount, unit) ) {
            return get();
        } else {
            throw new TimeoutException();
        }
    }

    @Override
    public T await() throws Exception {
        latch.await();
        return get();
    }

    private T get() throws Exception {
        Throwable e = error;
        if( e !=null ) {
            if( e instanceof RuntimeException ) {
                throw (RuntimeException) e;
            } else if( e instanceof Exception) {
                throw (Exception) e;
            } else if( e instanceof Error) {
                throw (Error) e;
            } else {
                // don't expect to hit this case.
                throw new RuntimeException(e);
            }
        }
        return value;
    }
}
