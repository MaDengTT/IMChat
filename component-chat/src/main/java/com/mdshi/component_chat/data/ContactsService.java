package com.mdshi.component_chat.data;

import android.arch.lifecycle.LiveData;

import com.mdshi.common.base.BaseBean;
import com.mdshi.common.db.entity.ContactsEntity;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by MaDeng on 2018/9/19.
 */
public interface ContactsService {
    @FormUrlEncoded
    @POST()
    public Flowable<List<ContactsEntity>> getContactsList(@Field("userid") long userid);
    @FormUrlEncoded
    @POST()
    public LiveData<BaseBean<List<ContactsEntity>>> getContactsListToLiveData(@Field("userid") long userid);
    @FormUrlEncoded
    @POST()
    public Flowable<List<ContactsEntity>> addContacts(@Field("userid") long userid,@Field("contactsid")long contactsid);
}
