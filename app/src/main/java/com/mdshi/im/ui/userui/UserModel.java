package com.mdshi.im.ui.userui;

import android.arch.lifecycle.ViewModel;

import com.mdshi.common.base.BaseBean;
import com.mdshi.common.constan.UserData;
import com.mdshi.common.db.entity.UserEntity;
import com.mdshi.common.rx.RxUtils;
import com.mdshi.im.data.UserRepository;

import org.reactivestreams.Publisher;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * Created by MaDeng on 2018/9/13.
 */
public class UserModel extends ViewModel {

    UserRepository repository;
    private UserData userData;

    @Inject
    public UserModel(UserRepository repository,UserData userData) {
        this.repository = repository;
        this.userData = userData;
    }

    public Flowable<BaseBean<UserEntity>> login(String phone, String email, String password) {
        return repository.login(phone,email,password)
                .map(userEntityBaseBean -> {
                    if (userEntityBaseBean.isSuccess()) {
                        userData.postValue(userEntityBaseBean.data);
                    }
                    return userEntityBaseBean;
                })
                .compose(RxUtils.switchMainThread())
                .onErrorReturn(RxUtils.baseBeanThrowable());
    }


    public Flowable<BaseBean<UserEntity>> register(String phone, String email, String password) {
        return repository.register(phone,email,password)
                .map(userEntityBaseBean -> {
                    if (userEntityBaseBean.isSuccess()) {
                        userData.postValue(userEntityBaseBean.data);
                    }
                    return userEntityBaseBean;
                })
                .compose(RxUtils.switchMainThread())
                .onErrorReturn(RxUtils.baseBeanThrowable());
    }


}
