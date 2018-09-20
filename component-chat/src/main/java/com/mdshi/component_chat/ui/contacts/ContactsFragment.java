package com.mdshi.component_chat.ui.contacts;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.mdshi.common.base.BaseFragment;
import com.mdshi.common.db.entity.ContactsEntity;
import com.mdshi.component_chat.R;
import com.mdshi.component_chat.adapter.ContactsAdapter;
import com.mdshi.component_chat.ui.ChatActivity;
import com.mdshi.component_chat.ui.chat.ChatActivityModel;

import java.util.List;

import javax.inject.Inject;


public class ContactsFragment extends BaseFragment {

    RecyclerView rvContacts;
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

    private void initData() {
        model.getDataList().observe(this, contactsEntities -> adapter.setNewData(contactsEntities));
    }

    private void initView() {
        rvContacts = root.findViewById(R.id.rv_contacts);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvContacts.setLayoutManager(linearLayoutManager);

        rvContacts.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                toChatActivity(((ContactsAdapter)adapter).getItem(position));
            }
        });
    }

    private void toChatActivity(ContactsEntity item) {
        ChatActivity.start(getActivity(),item.session_id);
    }


}
