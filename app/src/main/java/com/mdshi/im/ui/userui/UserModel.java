package com.mdshi.im.ui.userui;

import android.arch.lifecycle.ViewModel;

import com.mdshi.common.base.BaseBean;
import com.mdshi.common.db.entity.UserEntity;
import com.mdshi.im.data.UserRepository;

import org.reactivestreams.Publisher;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(throwable -> {
                    BaseBean bean = new BaseBean();
                    if (throwable instanceof HttpException) {
                        bean.code = 404;
                        bean.message = "网络异常";
                    }
                    return bean;
                });
    }


}
