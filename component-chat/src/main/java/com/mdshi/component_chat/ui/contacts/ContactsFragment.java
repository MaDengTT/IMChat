package com.mdshi.component_chat.ui.contacts;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.mdshi.common.base.BaseFragment;
import com.mdshi.common.constan.UserData;
import com.mdshi.common.db.entity.ContactsEntity;
import com.mdshi.common.db.entity.UserEntity;
import com.mdshi.component_chat.R;
import com.mdshi.component_chat.adapter.ContactsAdapter;
import com.mdshi.component_chat.ui.chat.ChatActivity;
import com.mdshi.common.vo.Status;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class ContactsFragment extends BaseFragment {

    RecyclerView rvContacts;
    SwipeRefreshLayout srl;
    private View root;

    ContactsModel model;
    ContactsAdapter adapter;


    @Inject
    ViewModelProvider.Factory factory;

    public static ContactsFragment newInstance(String param1, String param2) {
        ContactsFragment fragment = new ContactsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.chat_fragment_contacts, container, false);
        model = ViewModelProviders.of(this, factory).get(ContactsModel.class);
        initView();
        initData();
        return root;
    }

    private static final String TAG = "ContactsFragment";
    private void initData() {
        model.getContactsData().observe(this, listResource -> {
            if (listResource == null||listResource.status == Status.ERROR) {
                Log.e(TAG, "initData: ", listResource != null ? listResource.throwable : null);
                srl.setRefreshing(false);
            } else if(listResource.status == Status.LOADING){
                Log.d(TAG, "initData: Loading");
            } else if (listResource.status == Status.SUCCESS) {
                adapter.setNewData(listResource.data);
                srl.setRefreshing(false);
                Log.d(TAG, "initData: Success"+listResource.data.size());
            }
        });

//        getUserData().observe(this, userEntity -> model.setUserID(userEntity.userID));
    }

    private void initView() {
        rvContacts = root.findViewById(R.id.rv_contacts);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvContacts.setLayoutManager(linearLayoutManager);

        adapter = new ContactsAdapter(null);
        rvContacts.setAdapter(adapter);

        srl = root.findViewById(R.id.srl);

        srl.setOnRefreshListener(() -> model.retry());
        rvContacts.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                toChatActivity(((ContactsAdapter)adapter).getItem(position));
            }
        });
    }

    private void toChatActivity(ContactsEntity item) {
        ChatActivity.start(getActivity(),item.getSession_id(),item.contactsId);
    }


}
