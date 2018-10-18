package com.mdshi.im.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.mdshi.common.base.BaseBean;
import com.mdshi.common.db.dao.CircleDao;
import com.mdshi.common.db.entity.CircleEntity;
import com.mdshi.common.rx.RxUtils;
import com.mdshi.common.vo.AppExecutors;
import com.mdshi.common.vo.NetworkBoundResource;
import com.mdshi.common.vo.Resource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Single;

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
        return new NetworkBoundResource<List<CircleEntity>,List<CircleEntity>>(executors){

            @Override
            protected void saveCallResult(@NonNull List<CircleEntity> item) {
                dao.insert(item);
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
            protected LiveData<BaseBean<List<CircleEntity>>> createCall() {
                MutableLiveData<BaseBean<List<CircleEntity>>> liveData = new MutableLiveData<>();
                service.getCircleData(userID,20,0)
                        .subscribe(listBaseBean -> liveData.postValue(listBaseBean), throwable -> Log.e(TAG, "accept: ",throwable ));
                return liveData;
            }
        }.asLiveData();
    }

    public Flowable<BaseBean<CircleEntity>> uploadCircle(long userId, String content, List<String> images) {

        return service.upCircleData(userId, content, images).compose(RxUtils.switchMainThread());
    }

    private static final String TAG = "CircleRepository";
}
