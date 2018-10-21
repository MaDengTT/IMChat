package com.mdshi.component_chat.data;

import android.arch.lifecycle.LiveData;

import com.mdshi.common.base.BaseBean;
import com.mdshi.common.db.entity.ContactsEntity;
import com.mdshi.common.db.entity.UserEntity;

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
    @POST("/contacts/list")
    public Flowable<BaseBean<List<ContactsEntity>>> getContactsList(@Field("userId") long userid);
    @FormUrlEncoded
    @POST("/contacts/list")
    public LiveData<BaseBean<List<ContactsEntity>>> getContactsListToLiveData(@Field("userId") long userid);
    @FormUrlEncoded
    @POST("/contacts/add")
    public Flowable<BaseBean<List<ContactsEntity>>> addContacts(@Field("userId") long userid,@Field("contactsid")long contactsid);

    /**
     * 请求添加
     * @param userid
     * @param contactsid
     * @return
     */
    @FormUrlEncoded
    @POST("/contacts/apply")
    public Flowable<BaseBean<List<ContactsEntity>>> applyContacts(@Field("userId") long userid,@Field("contactsid")long contactsid);


}
