package com.mdshi.common.rx;


import com.mdshi.common.base.BaseBean;

import org.reactivestreams.Subscription;

import io.reactivex.FlowableSubscriber;
import io.reactivex.functions.Function;
import retrofit2.HttpException;


/**
 * Created by MaDeng on 2018/9/14.
 */
public abstract class RxSubscriber<T> implements FlowableSubscriber<T>{

    @Override
    public abstract void onSubscribe(Subscription s);

    @Override
    public void onNext(T t) {
        if (t instanceof BaseBean) {

        }else {

        }
    }

    public abstract void onNext();

    @Override
    public void onError(Throwable t) {

    }

    public abstract void onError(int code, String msg);

    @Override
    public abstract void onComplete();
}
