package com.mdshi.component_chat.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.mdshi.common.base.BaseBean;
import com.mdshi.common.db.dao.ContactsDao;
import com.mdshi.common.db.dao.UserDao;
import com.mdshi.common.db.entity.ContactsEntity;
import com.mdshi.common.db.entity.UserEntity;
import com.mdshi.common.rx.RxUtils;
import com.mdshi.common.vo.Resource;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import com.mdshi.common.vo.AppExecutors;
import com.mdshi.common.vo.NetworkBoundResource;
/**
 * Created by MaDeng on 2018/9/19.
 */
public class ContactsRepository {

    private final ContactsDao dao;
    private final ContactsService service;
    private final AppExecutors appExecutors;

    @Inject
    public ContactsRepository(ContactsDao dao, ContactsService service, AppExecutors appExecutors) {
        this.dao = dao;
        this.service = service;
        this.appExecutors = appExecutors;
    }

    private LiveData<List<ContactsEntity>> data;

    private static final String TAG = "ContactsRepository";

    public LiveData<Resource<List<ContactsEntity>>> getContactsData(long userId) {
        return new NetworkBoundResource<List<ContactsEntity>,List<ContactsEntity>>(appExecutors){

            @Override
            protected void saveCallResult(@NonNull List<ContactsEntity> item) {
                dao.insert(item);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<ContactsEntity> data) {
                return data == null||data.isEmpty();
            }

            @NonNull
            @Override
            protected LiveData<List<ContactsEntity>> loadFromDb() {
                return dao.findAllContacts(userId);
            }

            @NonNull
            @Override
            protected LiveData<BaseBean<List<ContactsEntity>>> createCall() {
                MutableLiveData<BaseBean<List<ContactsEntity>>> data = new MutableLiveData<>();
                service.getContactsList(userId)
                        .compose(RxUtils.switchMainThread())
                        .subscribe(contactsEntities -> {
                            data.setValue(contactsEntities);
                        },error->{
                            Log.e(TAG, "createCall: ",error );
                            BaseBean<List<ContactsEntity>> baseBean = new BaseBean<>(400, "error", null);
                            data.setValue(baseBean);
                        });
                return data;
            }
        }.asLiveData();
    }

    public LiveData<List<ContactsEntity>> getContactsData2(long userid) {
        if (data == null) {
            data = dao.findAllContacts(userid);
        }

        service.getContactsList(userid)
                .subscribeOn(Schedulers.io())
                .subscribe(/*new Subscriber<List<ContactsEntity>>() {
                    @Override
                    public void onSubscribe(Subscription s) {

                    }

                    @Override
                    public void onNext(List<ContactsEntity> contactsEntities) {
                        dao.insert(contactsEntities.toArray(new ContactsEntity[contactsEntities.size()]));
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e(TAG, "onError: ",t );
                    }

                    @Override
                    public void onComplete() {

                    }
                }*/);
        return data;
    }

    public void addContacts(ContactsEntity entity) {
        service.addContacts(entity.userId,entity.contactsId);
    }
}
