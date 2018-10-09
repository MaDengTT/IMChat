package com.mdshi.component_chat.ui.contacts;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.text.TextUtils;
import android.util.Log;

import com.mdshi.common.base.BaseBean;
import com.mdshi.common.constan.UserData;
import com.mdshi.common.db.entity.ContactsEntity;
import com.mdshi.common.db.entity.UserEntity;
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
    MutableLiveData<Search> search = new MutableLiveData<>();
    private final LiveData<BaseBean<List<UserEntity>>> searchData;

    public LiveData<Resource<List<ContactsEntity>>> getData() {
        return contactsData;
    }



    @Inject
    public ContactsModel(ContactsRepository repository, UserData userData) {
        this.repository = repository;
        contactsData = Transformations.switchMap(user,data->{
            if (data == null|| data == 0) {
                return AbsentLiveData.create();
            }else {
                return repository.getContactsData(data);
            }
        });

        searchData = Transformations.switchMap(search, data -> {
            if (data==null||TextUtils.isEmpty(data.search)) {
                return AbsentLiveData.create();
            } else {
                return repository.searchContacts(data.search,data.pagesize,data.pageno);
            }
        });

        userData.observeForever(s->user.setValue(s.userID));
    }

    public LiveData<BaseBean<List<UserEntity>>> getSearchData() {
        return searchData;
    }

    public LiveData<Resource<List<ContactsEntity>>> getContactsData() {
        return contactsData;
    }

    public void retry() {
        if (user.getValue() != null && user.getValue() != 0) {
            user.setValue(user.getValue());
        }
    }

    public void searchContacts(String s) {
        search.setValue(new Search(s));
    }

    public void searchNext() {
        search.setValue(search.getValue().next());
    }

    public void addContacts(long contactsId) {
        repository.addContacts(new ContactsEntity());
    }

    public void removeContacts(long contactsId){

    }

    public class Search {
        String search;
        int pagesize;
        int pageno;

        public Search next() {
            pageno++;
            return this;
        }

        public Search(String search) {
            this.search = search;
            this.pagesize = 20;
            this.pageno = 0;
        }
    }

}
