package com.mdshi.common.rx;

import android.util.Log;

import com.mdshi.common.base.BaseBean;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 * Created by MaDeng on 2018/9/14.
 */
public class RxUtils {

    public static <T>FlowableTransformer<T,T> switchMainThread(){
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> Function<Throwable, BaseBean<T>> baseBeanThrowable() {
        return new Function<Throwable, BaseBean<T>>() {
            @Override
            public BaseBean<T> apply(Throwable throwable) throws Exception {
                Log.e("baseBeanThrowable", "apply: ",throwable );
                BaseBean bean = new BaseBean();
                if (throwable instanceof HttpException) {
                    bean.code = 404;
                    bean.message = "网络异常";
                }else {
                    bean.code = 422;
                    bean.message = "其他错误";
                }
                return bean;
            }
        };
    }
}
