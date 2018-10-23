package com.mdshi.component_chat.ui.contacts;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.mdshi.common.base.BaseFragment;
import com.mdshi.common.db.entity.ContactsEntity;
import com.mdshi.common.route.RouteContact;
import com.mdshi.component_chat.R;
import com.mdshi.component_chat.adapter.ContactsAdapter;

import com.mdshi.common.vo.Status;

import javax.inject.Inject;


public class ContactsFragment extends BaseFragment {

    RecyclerView rvContacts;
    SwipeRefreshLayout srl;
    private View root;

    ContactsModel model;

    @Inject
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
            if (listResource.data != null) {
                adapter.setNewData(listResource.data);
            }
            if (listResource == null||listResource.status == Status.ERROR) {
                srl.setRefreshing(false);
            } else if(listResource.status == Status.LOADING){

            } else if (listResource.status == Status.SUCCESS) {
                srl.setRefreshing(false);
            }
        });

//        getUserData().observe(this, userEntity -> model.setUserID(userEntity.userId));
    }

    private void initView() {

        TextView title = root.findViewById(R.id.tv_title);
        title.setText("联系人");
        View add = root.findViewById(R.id.iv_add);
        add.setVisibility(View.VISIBLE);
        add.setOnClickListener(v -> ARouter.getInstance().build(RouteContact.searchUser).navigation());

        rvContacts = root.findViewById(R.id.rv_contacts);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvContacts.setLayoutManager(linearLayoutManager);
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rvContacts.addItemDecoration(decoration);

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
//        ChatActivity.start(getActivity(),item.getSession_id(),item.contactsId);
        ContactsInfoActivity.start(getActivity(),item.contactsId);
    }


}
