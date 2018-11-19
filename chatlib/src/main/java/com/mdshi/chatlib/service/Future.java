package com.mdshi.chatlib.service;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

import com.mdshi.chatlib.listener.AbortableFuture;
import com.mdshi.chatlib.listener.RequestCallback;

import java.util.concurrent.Executor;

/**
 * Created by MaDeng on 2018/11/19.
 */
public class Future<T> implements AbortableFuture<T> {

    MainThreadExecutor executor = new MainThreadExecutor();

    private T data;
    private int code;
    private Throwable throwable;
    private RequestCallback callback;
    private STATUE statue = STATUE.NULL;
    enum STATUE {
        NULL,
        SUCCESS,
        FAILED,
        EXCEPTION
    }

    public void postData(final T data){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                setData(data);
            }
        });
    }
    public void postException(final int code,final Throwable throwable){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                setException(code,throwable);
            }
        });
    }

    private void setException(int code, Throwable throwable) {
        if (throwable != null) {
            this.throwable = throwable;
            statue = STATUE.EXCEPTION;
            active();
        }else {
            this.code = code;
            statue = STATUE.FAILED;
            active();
        }
    }

    @MainThread
    public void setData(T data){
        this.data = data;
        statue = STATUE.SUCCESS;
        active();
    }

    @Override
    public void setCallback(RequestCallback callback) {
        if (callback != null) {
            this.callback = callback;
        }
        if (statue != null) {
            active();
        }
    }

    private void active() {
        if (callback == null) {
            return;
        }
        switch (statue) {
            case NULL:
                break;
            case FAILED:
                callback.onFailde(code);
                break;
            case SUCCESS:
                callback.onSuccess(data);
                break;
            case EXCEPTION:
                callback.onException(throwable);
                break;
        }
        statue=STATUE.NULL;
    }

    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());
        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}
