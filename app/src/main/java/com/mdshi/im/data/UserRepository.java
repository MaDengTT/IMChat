package com.mdshi.im.data;

import android.arch.lifecycle.LiveData;

import com.mdshi.common.base.BaseBean;
import com.mdshi.common.db.dao.UserDao;
import com.mdshi.common.db.entity.UserEntity;

import org.reactivestreams.Publisher;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import retrofit2.Retrofit;

/**
 * Created by MaDeng on 2018/9/13.
 */
public class UserRepository {

    UserDao dao;
    UserService service;
    UserData userData;
    @Inject
    public UserRepository(UserDao dao, Retrofit retrofit) {
        this.dao = dao;
        service = retrofit.create(UserService.class);
    }

    public Flowable<BaseBean<UserEntity>> login(String phone, String email, String password) {
        return service.login(phone, email, password).flatMap(userEntityBaseBean -> {
            if (userEntityBaseBean.isSuccess()) {
                UserEntity user = dao.getUser(userEntityBaseBean.data.id);
                if (user != null) {
                    dao.updateUser(userEntityBaseBean.data);
                }else {
                    dao.insertUser(userEntityBaseBean.data);
                }
                userData.postValue(userEntityBaseBean.data);
            }
            return Flowable.just(userEntityBaseBean);
        });
    }

    public UserData getUserData() {
        if (userData == null) {
            userData = new UserData();
        }
        return userData;
    }

}
