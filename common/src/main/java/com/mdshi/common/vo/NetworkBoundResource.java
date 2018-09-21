package com.mdshi.common.vo;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import com.mdshi.common.base.BaseBean;

import java.util.Objects;

/**
 * Created by MaDeng on 2018/9/21.
 */
public abstract class NetworkBoundResource<ResultType,RequestType> {
    private final AppExecutors appExecutors;

    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    @MainThread
    public NetworkBoundResource(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
        result.setValue(Resource.loading(null));
        LiveData<ResultType> dbSource = loadFromDb();
        result.addSource(dbSource,data->{
            result.removeSource(dbSource);
            if(shouldFetch(data)){
                fetchFormNetWork(dbSource);
            }else {
                result.addSource(dbSource,newData->setValue(Resource.success(newData)));
            }
        });
    }

    @MainThread
    private void setValue(Resource<ResultType> newValue) {
        if (!Objects.equals(result.getValue(), newValue)) {
            result.setValue(newValue);
        }
    }

    private void fetchFormNetWork(LiveData<ResultType> dbSource) {
        LiveData<BaseBean<RequestType>> apiResponse = createCall();
        result.addSource(dbSource,newData->setValue(Resource.loading(newData)));
        result.addSource(apiResponse,response->{
            result.removeSource(apiResponse);
            result.removeSource(dbSource);
            if(response!=null&&response.isSuccess()){
                appExecutors.diskIO().execute(()->{

                    saveCallResult(processResponse(response));
                    appExecutors.mainThread().execute(()->
                    result.addSource(loadFromDb(),newData->setValue(Resource.success(newData))));
                });
            }else {
                onFetchFailed();
                result.addSource(dbSource, newData -> setValue(Resource.error(response==null?"response is null":response.message,newData)));
            }
        });
    }

    protected void onFetchFailed() {
    }

    public LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }
    @WorkerThread
    protected RequestType processResponse(BaseBean<RequestType> response) {
        return response.data;
    }

    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);


    @MainThread
    protected abstract boolean shouldFetch(@Nullable ResultType data);

    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    @NonNull
    @MainThread
    protected abstract LiveData<BaseBean<RequestType>> createCall();
}
