package com.mdshi.common.base;

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

    @Override
    public String toString() {
        return "BaseBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
