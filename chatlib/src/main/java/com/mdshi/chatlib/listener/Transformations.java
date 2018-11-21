package com.mdshi.chatlib.listener;

import android.widget.Switch;



/**
 * Created by MaDeng on 2018/11/21.
 */
public class Transformations {

    public static <X,Y> RequestCallback<Y> map(final RequestCallback<X> trigger, final Function<Y,X> func) {
        RequestCallback<Y> requestCallback = new RequestCallback<Y>() {

            @Override
            public void onSuccess(Y param) {
                trigger.onSuccess(func.apply(param));
            }

            @Override
            public void onFailed(int code) {
                trigger.onFailed(code);
            }

            @Override
            public void onException(Throwable exception) {
                trigger.onException(exception);
            }
        };

        return requestCallback;
    }

}
