package com.mdshi.component_chat.data;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.mdshi.common.base.BaseBean;
import com.mdshi.common.db.dao.ContactsDao;
import com.mdshi.common.db.entity.ContactsEntity;
import com.mdshi.common.vo.Resource;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

import javax.inject.Inject;

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

    public LiveData<Resource<List<ContactsEntity>>> getContactsData(long userid) {
        return new NetworkBoundResource<List<ContactsEntity>,List<ContactsEntity>>(appExecutors){

            @Override
            protected void saveCallResult(@NonNull List<ContactsEntity> item) {

            }

            @Override
            protected boolean shouldFetch(@Nullable List<ContactsEntity> data) {
                return data == null;
            }

            @NonNull
            @Override
            protected LiveData<List<ContactsEntity>> loadFromDb() {
                return dao.findAllContacts(userid);
            }

            @NonNull
            @Override
            protected LiveData<BaseBean<List<ContactsEntity>>> createCall() {
                return service.getContactsListToLiveData(userid);
            }
        }.asLiveData();
    }

    public LiveData<List<ContactsEntity>> getContactsData2(long userid) {
        if (data == null) {
            data = dao.findAllContacts(userid);
        }

        service.getContactsList(userid)
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<ContactsEntity>>() {
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
                });
        return data;
    }

    public void addContacts(ContactsEntity entity) {
        service.addContacts(entity.userid,entity.contactsid);
    }
}
