package com.mdshi.im.ui.show;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.mdshi.common.constan.UserData;
import com.mdshi.common.db.entity.CircleEntity;
import com.mdshi.common.db.entity.UserEntity;
import com.mdshi.common.vo.AbsentLiveData;
import com.mdshi.common.vo.Resource;
import com.mdshi.im.data.CircleRepository;

import org.reactivestreams.Publisher;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.functions.Action;

public class ShowViewModel extends ViewModel {

    // TODO: Implement the ViewModel
    UserData userData;

    private LiveData<Resource<List<CircleEntity>>> circleData;
    private CircleRepository repository;

    @Inject
    public ShowViewModel(UserData userData) {
        this.userData = userData;
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

    public void upload(String content, List<String> images) {
        Flowable.fromIterable(images)
                .flatMap(new io.reactivex.functions.Function<String, Publisher<?>>() {
                    @Override
                    public Publisher<?> apply(String s) throws Exception {
                        return null;
                    }
                });
        repository.uploadCircle(userData.getValue().userID,content,images);
    }


}
