package com.mdshi.component_chat.data;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.mdshi.common.db.dao.ContactsDao;
import com.mdshi.common.db.entity.ContactsEntity;
import com.mdshi.common.rx.RxUtils;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.schedulers.Schedulers;

/**
 * Created by MaDeng on 2018/9/19.
 */
public class ContactsRepository {

    ContactsDao dao;
    ContactsService service;
    private LiveData<List<ContactsEntity>> data;

    private static final String TAG = "ContactsRepository";

    public ContactsRepository(ContactsDao dao,ContactsService service) {
        this.dao = dao;
        this.service = service;
    }

    public LiveData<List<ContactsEntity>> getContactsData(long userid) {
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
