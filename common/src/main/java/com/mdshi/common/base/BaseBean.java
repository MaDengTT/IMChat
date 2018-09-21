package com.mdshi.common.base;

import java.util.Collections;

import io.reactivex.annotations.Nullable;

/**
 * Created by MaDeng on 2018/8/31.
 */
public class BaseBean<T> {
    public int code;
    public String message;
    public T data;

    public boolean isSuccess() {
        return code == 200;
    }

    public BaseBean(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public BaseBean(Throwable error) {
        code = 400;
        data = null;
        message = error.getMessage();
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
