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
import com.mdshi.common.vo.AbsentLiveData;
import com.mdshi.common.vo.Resource;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
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
    private ContactsDao contactsDao;
    @Inject
    public ContactsRepository(ContactsDao dao, ContactsService service, AppExecutors appExecutors,ContactsDao contactsDao) {
        this.dao = dao;
        this.service = service;
        this.appExecutors = appExecutors;
        this.contactsDao = contactsDao;
    }

    private LiveData<List<ContactsEntity>> data;

    private static final String TAG = "ContactsRepository";


    public LiveData<Resource<List<ContactsEntity>>> getContactsData(long userId) {
        return new NetworkBoundResource<List<ContactsEntity>,List<ContactsEntity>>(appExecutors){

            @Override
            protected void saveCallResult(@NonNull List<ContactsEntity> item) {
                dao.updateContacts(item,userId);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<ContactsEntity> data) {
//                return data == null||data.isEmpty();
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<ContactsEntity>> loadFromDb() {
                return dao.findAllContacts(userId);
            }

            @NonNull
            @Override
            protected LiveData<BaseBean<List<ContactsEntity>>> createCall() {
                return service.getContactsListToLiveData(userId);
            }
        }.asLiveData();
    }

    public void addContacts(ContactsEntity entity) {
        service.addContacts(entity.userId,entity.contactsId);
    }

    public LiveData<ContactsEntity> getContact(long user, long contactsId) {
        return contactsDao.findContactsToLiveData(user,contactsId);
    }
}
