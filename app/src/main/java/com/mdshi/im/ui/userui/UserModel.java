package com.mdshi.im.ui.userui;

import android.arch.lifecycle.ViewModel;

import com.mdshi.common.base.BaseBean;
import com.mdshi.common.db.entity.UserEntity;
import com.mdshi.common.rx.RxUtils;
import com.mdshi.im.data.UserRepository;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by MaDeng on 2018/9/13.
 */
public class UserModel extends ViewModel {

    UserRepository repository;

    @Inject
    public UserModel(UserRepository repository) {
        this.repository = repository;
    }

    public Flowable<BaseBean<UserEntity>> login(String phone, String email, String password) {
        return repository.login(phone,email,password)
                .compose(RxUtils.switchMainThread())
                .onErrorReturn(RxUtils.baseBeanThrowable());
    }


    public Flowable<BaseBean<UserEntity>> register(String phone, String email, String password) {
        return repository.register(phone,email,password)
                .compose(RxUtils.switchMainThread())
                .onErrorReturn(RxUtils.baseBeanThrowable());
    }


}
