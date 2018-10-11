package com.mdshi.im.ui.userui;

import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.mdshi.common.base.BaseBean;
import com.mdshi.common.constan.UserData;
import com.mdshi.common.db.entity.UserEntity;
import com.mdshi.common.rx.RxUtils;
import com.mdshi.im.data.UserRepository;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileCallback;

import org.reactivestreams.Publisher;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by MaDeng on 2018/9/13.
 */
public class UserModel extends ViewModel {

    UserRepository repository;
    private UserData userData;

    @Inject
    public UserModel(UserRepository repository,UserData userData) {
        this.repository = repository;
        this.userData = userData;
    }

    public Flowable<BaseBean<UserEntity>> login(String phone, String email, String password) {
        return repository.login(phone,email,password)
                .map(userEntityBaseBean -> {
                    if (userEntityBaseBean.isSuccess()) {
                        userData.postValue(userEntityBaseBean.data);
                    }
                    return userEntityBaseBean;
                })
                .compose(RxUtils.switchMainThread())
                .onErrorReturn(RxUtils.baseBeanThrowable());
    }


    public Flowable<BaseBean<UserEntity>> register(String phone, String email, String password) {
        return repository.register(phone,email,password)
                .map(userEntityBaseBean -> {
                    if (userEntityBaseBean.isSuccess()) {
                        userData.postValue(userEntityBaseBean.data);
                    }
                    return userEntityBaseBean;
                })
                .compose(RxUtils.switchMainThread())
                .onErrorReturn(RxUtils.baseBeanThrowable());
    }

    private static final String TAG = "UserModel";
    public void setUserImage(String image){
        Flowable.just(image)
                .map(File::new)
                .flatMap((Function<File, Publisher<String>>) file -> Flowable.create(emitter -> {
                    Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
                    Tiny.getInstance().source(file).asFile().withOptions(options)
                            .compress((isSuccess, outfile, t) -> {
                                if (isSuccess) {
                                    Log.d(TAG, "setUserImage: "+outfile);
                                    emitter.onNext(outfile);
                                }else {
                                    emitter.onError(t);
                                }
                            });
                },BackpressureStrategy.BUFFER))
                .flatMap(img->repository.uploadFile(img))
                .compose(RxUtils.switchMainThread())
                .subscribe(stringBaseBean -> {
                    if (stringBaseBean.isSuccess()) {
                        Log.d(TAG, "accept: "+stringBaseBean);
                    }else {
                        Log.e(TAG, "accept: "+stringBaseBean.message );
                    }
                }, throwable -> Log.e(TAG, "accept: ",throwable ));
    }

}
