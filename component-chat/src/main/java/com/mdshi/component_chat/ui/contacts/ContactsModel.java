package com.mdshi.component_chat.ui.contacts;

import android.arch.lifecycle.LiveData;

import com.mdshi.common.db.entity.ContactsEntity;
import com.mdshi.component_chat.data.ContactsRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by MaDeng on 2018/9/19.
 */
public class ContactsModel {

    ContactsRepository repository;
    private long userid;

    @Inject
    public ContactsModel(ContactsRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<ContactsEntity>> getDataList() {
        return repository.getContactsData(userid);
    }

    public void addContacts(long contactsId) {
        repository.addContacts(new ContactsEntity());
    }
}
