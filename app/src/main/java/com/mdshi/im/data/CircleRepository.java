package com.mdshi.im.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.util.Log;

import com.mdshi.common.base.BaseBean;
import com.mdshi.common.db.dao.CircleDao;
import com.mdshi.common.db.entity.CircleEntity;
import com.mdshi.common.rx.RxUtils;
import com.mdshi.common.utils.TimeUtils;
import com.mdshi.common.vo.AppExecutors;
import com.mdshi.common.vo.NetworkBoundResource;
import com.mdshi.common.vo.Resource;
import com.mdshi.im.bean.CircleBean;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;


/**
 * Created by MaDeng on 2018/10/18.
 */
public class CircleRepository {

    CircleDao dao;
    AppExecutors executors;
    CircleService service;
    @Inject
    public CircleRepository(CircleDao dao, AppExecutors executors,CircleService service) {
        this.dao = dao;
        this.executors = executors;
        this.service = service;
    }

    public LiveData<Resource<List<CircleEntity>>> getCircleData(long userID) {
        return new NetworkBoundResource<List<CircleEntity>,List<CircleBean>>(executors){
            @Override
            protected void saveCallResult(@NonNull List<CircleBean> item) {
                for (CircleBean bean:item) {
                    circleDataToDb(bean);
                }
            }
            @Override
            protected boolean shouldFetch(@Nullable List<CircleEntity> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<CircleEntity>> loadFromDb() {
                return dao.getCircleAll();
            }

            @NonNull
            @Override
            protected LiveData<BaseBean<List<CircleBean>>> createCall() {
                MutableLiveData<BaseBean<List<CircleBean>>> liveData = new MutableLiveData<>();
                service.getCircleData(userID,20,1)
                        .compose(RxUtils.switchMainThread())
                        .subscribe(listBaseBean -> liveData.postValue(listBaseBean), throwable -> Log.e(TAG, "accept: ",throwable ));
                return liveData;
            }

        }.asLiveData();
    }

    public Flowable<BaseBean<CircleBean>> uploadCircle(long userId, String content, String images) {

        return service.upCircleData(userId, content, images);
    }
    public void circleDataToDb(CircleBean bean) {
        dao.insert(new CircleEntity(bean.getId(),
                bean.getUserId(),
                bean.getUserInfo().userName,
                bean.getUserInfo().avatar,
                bean.getContentText(),
                bean.getImgUrls(),
                TimeUtils.string2Date(bean.getCreateTime(),TimeUtils.DEFAULT_FORMAT_T_Z)));
    }

    private static final String TAG = "CircleRepository";
}
