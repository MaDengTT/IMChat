package com.mdshi.im.data;

import android.arch.lifecycle.LiveData;

import com.mdshi.common.base.BaseBean;
import com.mdshi.common.db.entity.CircleEntity;
import com.mdshi.im.bean.CircleBean;

import java.util.List;

import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by MaDeng on 2018/10/18.
 */
public interface CircleService {

    @FormUrlEncoded
    @POST("/circle/list")
    LiveData<BaseBean<List<CircleBean>>> getCircleData(@Field("userid") long useId, @Field("pagesize") int pageSize, @Field("pageno") int pageNo);
    @FormUrlEncoded
    @POST("/circle/add")
    Flowable<BaseBean<CircleBean>> upCircleData(@Field("userid")long userId,@Field("content") String content,@Field("images") String images);
}
