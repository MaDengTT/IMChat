package com.mdshi.common.mvp;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;


import com.mdshi.common.utils.Preconditions;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by MaDeng on 2018/5/7.
 */
public class BasePresenter<M extends IModel,V extends IView> implements IPresenter,LifecycleObserver {

    protected final String TAG = this.getClass().getSimpleName();
    protected CompositeDisposable mCompositeDisposable;

    protected M mModel;
    protected V mView;


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate(LifecycleOwner owner) {

    }

    /**
     * 如果当前页面同时需要 Model 层和 View 层,则使用此构造函数(默认)
     *
     * @param model
     * @param rootView
     */
    public BasePresenter(M model, V rootView) {
        Preconditions.checkNotNull(model, "%s cannot be null", IModel.class.getName());
        Preconditions.checkNotNull(rootView, "%s cannot be null", IView.class.getName());
        this.mModel = model;
        this.mView = rootView;
        onStart();
    }

    /**
     * 如果当前页面不需要操作数据,只需要 View 层,则使用此构造函数
     *
     * @param rootView
     */
    public BasePresenter(V rootView) {
        Preconditions.checkNotNull(rootView, "%s cannot be null", IView.class.getName());
        this.mView = rootView;
        onStart();
    }

    public BasePresenter() {
        onStart();
    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {

    }

    @Override
    public void onDestroy() {
        if (mModel != null)
            mModel.onDestroy();
        this.mModel = null;
        this.mView = null;
        this.mCompositeDisposable = null;
    }
}
