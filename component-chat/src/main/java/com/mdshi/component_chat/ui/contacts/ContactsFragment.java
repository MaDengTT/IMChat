package com.mdshi.component_chat.ui.contacts;


import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mdshi.common.base.BaseFragment;
import com.mdshi.common.db.entity.ContactsEntity;
import com.mdshi.component_chat.R;
import com.mdshi.component_chat.adapter.ContactsAdapter;

import java.util.List;


public class ContactsFragment extends BaseFragment {

    RecyclerView rvContacts;
    private View root;

    ContactsModel model;
    ContactsAdapter adapter;

    public static ContactsFragment newInstance(String param1, String param2) {
        ContactsFragment fragment = new ContactsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.chat_fragment_contacts, container, false);
        initView();
        initData();
        return root;
    }

    private void initData() {
        model.getDataList().observe(this, contactsEntities -> adapter.setNewData(contactsEntities));
    }

    private void initView() {
        rvContacts = root.findViewById(R.id.rv_contacts);
    }


}
