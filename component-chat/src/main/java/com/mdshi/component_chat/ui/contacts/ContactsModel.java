package com.mdshi.component_chat.ui.contacts;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.mdshi.common.constan.UserData;
import com.mdshi.common.db.entity.ContactsEntity;
import com.mdshi.component_chat.data.ContactsRepository;
import com.mdshi.common.vo.AbsentLiveData;
import com.mdshi.common.vo.Resource;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by MaDeng on 2018/9/19.
 */
public class ContactsModel extends ViewModel{

    ContactsRepository repository;

    LiveData<Resource<List<ContactsEntity>>> contactsData;
    MutableLiveData<Long> user = new MutableLiveData<>();

    public LiveData<Resource<List<ContactsEntity>>> getData() {
        return contactsData;
    }



    @Inject
    public ContactsModel(ContactsRepository repository, UserData userData) {
        this.repository = repository;
        contactsData = Transformations.switchMap(userData,data->{
            if (data == null|| data.userId == 0) {
                return AbsentLiveData.create();
            }else {
                return repository.getContactsData(data.userId);
            }
        });



        userData.observeForever(s->{
            if(s!=null){user.setValue(s.userId);}
        });
    }



    public LiveData<Resource<List<ContactsEntity>>> getContactsData() {
        return contactsData;
    }

    public void retry() {
        if (user.getValue() != null && user.getValue() != 0) {
            user.setValue(user.getValue());
        }
    }

    public LiveData<ContactsEntity> getContact(long contactsId){
        return repository.getContact(user.getValue(),contactsId);
    }


    public void addContacts(long contactsId) {
        repository.addContacts(new ContactsEntity());
    }

    public void removeContacts(long contactsId){

    }



}
