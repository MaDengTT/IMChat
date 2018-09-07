package com.mdshi.common.bus;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.functions.Predicate;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by MaDeng on 2018/9/7.
 */
public class RxBus {

    private static volatile RxBus instance;
    private FlowableProcessor<Object> bus;


    public static RxBus getInstance() {
        if (instance == null) {
            synchronized (RxBus.class) {
                if (instance == null) {
                    instance = new RxBus();
                }
            }
        }
        return instance;
    }

    public RxBus() {
        bus = PublishProcessor.create().toSerialized();
    }

    public void post(Object o) {
        bus.onNext(o);
    }

    public <T> Flowable<T> register(Class<T> clz){
        return bus.ofType(clz);
    }

    public boolean hasSubscribers() {
        return bus.hasSubscribers();
    }


}
