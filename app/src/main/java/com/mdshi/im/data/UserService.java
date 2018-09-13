package com.mdshi.im.data;

import com.mdshi.common.base.BaseBean;
import com.mdshi.common.db.entity.UserEntity;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by MaDeng on 2018/9/13.
 */
public interface UserService {
    @FormUrlEncoded
    @POST("/user/login")
    Flowable<BaseBean<UserEntity>> login(@Field("phone") String phone,
                                           @Field("email")String email,
                                           @Field("password")String password);

}
