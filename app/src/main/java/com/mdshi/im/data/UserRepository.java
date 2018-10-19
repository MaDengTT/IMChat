package com.mdshi.im.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.net.Uri;
import android.util.Log;

import com.mdshi.common.base.BaseBean;
import com.mdshi.common.constan.UserData;
import com.mdshi.common.db.dao.UserDao;
import com.mdshi.common.db.entity.UserEntity;
import com.mdshi.common.rx.RxUtils;
import com.mdshi.common.utils.FileUtils;

import java.io.File;
import java.util.List;

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
    public UserRepository(UserDao dao, UserService userService,UserData userData) {
        this.dao = dao;
        this.userData = userData;
        service = userService;
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

    public Flowable<BaseBean<UserEntity>> updateUserAvatar(long userId,String avatar){
        return service.updateUser(userId,null,avatar).subscribeOn(Schedulers.io());
    }

    public Flowable<BaseBean<UserEntity>> updateUserName(long userId, String userName) {
        return service.updateUser(userId, userName, null).subscribeOn(Schedulers.io());
    }


    public LiveData<BaseBean<List<UserEntity>>> searchContacts(String s, int pagesize, int pageno) {
        MutableLiveData<BaseBean<List<UserEntity>>> data = new MutableLiveData<>();
        service.searchContacts(s,pagesize,pageno)
                .compose(RxUtils.switchMainThread())
                .subscribe(contactsEntities -> {
                    data.setValue(contactsEntities);
                }, error -> {
//                    Log.e(TAG, "createCall: ", error);
                    BaseBean<List<UserEntity>> baseBean = new BaseBean<>(400, "error", null);
                    data.setValue(baseBean);
                });
        return data;

    }
}
