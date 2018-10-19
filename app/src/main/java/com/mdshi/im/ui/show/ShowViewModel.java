package com.mdshi.im.ui.show;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.mdshi.common.base.BaseBean;
import com.mdshi.common.constan.UserData;
import com.mdshi.common.db.entity.CircleEntity;

import com.mdshi.common.rx.RxUtils;
import com.mdshi.common.vo.AbsentLiveData;
import com.mdshi.common.vo.Resource;

import com.mdshi.im.bean.CircleBean;
import com.mdshi.im.data.CircleRepository;
import com.mdshi.im.data.FileRepository;

import org.reactivestreams.Subscription;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.functions.Consumer;


public class ShowViewModel extends ViewModel {

    // TODO: Implement the ViewModel
    UserData userData;

    private LiveData<Resource<List<CircleEntity>>> circleData;
    private CircleRepository repository;
    private MutableLiveData<Resource<CircleBean>> upLoadData = new MutableLiveData<>();
    FileRepository fileRepository;

    @Inject
    public ShowViewModel(UserData userData, CircleRepository repository, FileRepository fileRepository) {
        this.repository = repository;
        this.userData = userData;
        this.fileRepository = fileRepository;
        circleData = Transformations.switchMap(userData,input -> {
            if (input == null) {
                return AbsentLiveData.create();
            }else {
                return repository.getCircleData(input.userID);
            }
        });
    }

    public LiveData<Resource<List<CircleEntity>>> getCircleData() {
        return circleData;
    }

    public MutableLiveData<Resource<CircleBean>> getUpLoadData() {
        return upLoadData;
    }

    private static final String TAG = "ShowViewModel";

    public void upload(String content, List<String> images) {
        Flowable.fromIterable(images)
                .flatMap(s -> fileRepository.uploadFile(s).map(bean -> bean.data))
                .toList()
                .map(strings -> {
                    StringBuffer buffer = new StringBuffer();
                    for (int i = 0; i < strings.size(); i++) {
                        buffer.append(strings.get(i));
                        if (i < strings.size() - 1) {
                            buffer.append(",");
                        }
                    }
                    Log.d("11", "upload: "+buffer);
                    return buffer.toString();
                })
                .toFlowable()
                .flatMap( s -> repository.uploadCircle(userData.getValue().userID, content, s)
                        .map( circleBeanBaseBean -> {
                            if(circleBeanBaseBean.isSuccess()){
                                CircleBean bean = circleBeanBaseBean.data;
                                bean.setUserInfo(userData.getValue());
                                repository.circleDataToDb(bean);
                            }
                            return circleBeanBaseBean;
                        })
                )
                .doOnNext(circleBeanBaseBean -> {
                    if (circleBeanBaseBean.isSuccess()) {
                        upLoadData.postValue(Resource.success(circleBeanBaseBean.data));
                    }else {
                        upLoadData.postValue(Resource.error(circleBeanBaseBean.message,null));
                    }
                })
                .compose(RxUtils.switchMainThread())
                .subscribe(new FlowableSubscriber<BaseBean<CircleBean>>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d(TAG, "onSubscribe: ");
                        upLoadData.setValue(Resource.loading(null));
                    }

                    @Override
                    public void onNext(BaseBean<CircleBean> circleBeanBaseBean) {
                        Log.d(TAG, "onNext: ");
                        if (circleBeanBaseBean.isSuccess()) {
                            upLoadData.setValue(Resource.success(circleBeanBaseBean.data));
                        }else {
                            upLoadData.setValue(Resource.error(circleBeanBaseBean.message,null));
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e(TAG, "onError: ",t );
                        upLoadData.setValue(Resource.error(t.getMessage(),null));
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }


}
