package com.mdshi.im.data;

import android.net.Uri;

import com.mdshi.common.base.BaseBean;
import com.mdshi.common.constan.UserData;
import com.mdshi.common.db.dao.UserDao;
import com.mdshi.common.db.entity.UserEntity;
import com.mdshi.common.utils.FileUtils;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.Flowable;

import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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


    public Flowable<BaseBean<String>> uploadFile(String fileUri){
        File file = FileUtils.getFileByPath(fileUri);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        return service.upload(body).subscribeOn(Schedulers.io());
    }

}
