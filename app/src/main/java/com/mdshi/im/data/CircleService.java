package com.mdshi.im.data;

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
    Flowable<BaseBean<List<CircleBean>>> getCircleData(@Field("userId") long useId,@Field("pagesize") int pageSize,@Field("pageno") int pageNo);
    @FormUrlEncoded
    @POST("/circle/add")
    Flowable<BaseBean<CircleBean>> upCircleData(@Field("userId")long userId,@Field("content") String content,@Field("images") String images);
}
