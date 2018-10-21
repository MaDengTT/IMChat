package com.mdshi.im.data;

import com.mdshi.common.base.BaseBean;
import com.mdshi.common.db.entity.UserEntity;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
    @FormUrlEncoded
    @POST("/user/register")
    Flowable<BaseBean<UserEntity>> register(@Field("phone") String phone,
                                           @Field("email")String email,
                                           @Field("password")String password);

    @Multipart
    @POST("/file_upload")
    Flowable<BaseBean<String>> upload(@Part MultipartBody.Part file);

    @FormUrlEncoded
    @POST("/user/update")
    Flowable<BaseBean<UserEntity>> updateUser(@Field("userId")long userid,
                                              @Field("userName")String userName,
                                              @Field("avatar")String avatar);

    @FormUrlEncoded
    @POST("/user/search")
    public Flowable<BaseBean<List<UserEntity>>> searchContacts(@Field("search") String search, @Field("pagesize")int pagesize, @Field("pageno")int pageno);
}
