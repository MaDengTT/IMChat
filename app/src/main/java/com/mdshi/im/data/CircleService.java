package com.mdshi.im.data;

import com.mdshi.common.base.BaseBean;
import com.mdshi.common.db.entity.CircleEntity;

import java.util.List;

import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by MaDeng on 2018/10/18.
 */
public interface CircleService {

    @FormUrlEncoded
    @POST("/circle/list")
    Single<BaseBean<List<CircleEntity>>> getCircleData(long useId, int pageSize, int pageNo);
    @FormUrlEncoded
    @POST("/circle/up")
    Flowable<BaseBean<CircleEntity>> upCircleData(long userId, String content, List<String> images);
}
