package com.mdshi.im.data;

import com.mdshi.common.base.BaseBean;
import com.mdshi.common.constan.UserData;
import com.mdshi.common.db.dao.UserDao;
import com.mdshi.common.db.entity.UserEntity;

import javax.inject.Inject;

import io.reactivex.Flowable;

import retrofit2.Retrofit;

/**
 * Created by MaDeng on 2018/9/13.
 */
public class UserRepository {

    UserDao dao;
    UserService service;
    UserData userData;
    @Inject
    public UserRepository(UserDao dao, Retrofit retrofit,UserData userData) {
        this.dao = dao;
        this.userData = userData;
        service = retrofit.create(UserService.class);
    }

    public Flowable<BaseBean<UserEntity>> login(String phone, String email, String password) {
        return service.login(phone, email, password).flatMap(userEntityBaseBean -> {
            if (userEntityBaseBean.isSuccess()) {
                UserEntity user = dao.getUser(userEntityBaseBean.data.userID);
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

    public Flowable<BaseBean<UserEntity>> register(String phone, String email, String password) {
        return service.register(phone, email, password).flatMap(userEntityBaseBean -> {
            if (userEntityBaseBean.isSuccess()) {
                dao.insertUser(userEntityBaseBean.data);
                userData.postValue(userEntityBaseBean.data);
            }
            return Flowable.just(userEntityBaseBean);
        });
    }



}
