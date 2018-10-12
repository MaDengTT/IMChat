package com.mdshi.im.ui.search;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mdshi.common.base.BaseActivity;
import com.mdshi.common.route.RouteContact;
import com.mdshi.im.R;


import javax.inject.Inject;

@Route(path = RouteContact.searchUser)
public class SearchContactsActivity extends BaseActivity {

    SearchModel model;

    EditText edSearch;
    RecyclerView rvUsers;

    public static void start(Context context) {
        Intent starter = new Intent(context, SearchContactsActivity.class);
        context.startActivity(starter);
    }

    @Inject
    ViewModelProvider.Factory factory;

    @Inject
    SearchUserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_contacts);
        model = ViewModelProviders.of(this, factory).get(SearchModel.class);
        initView();
        initData();
    }

    private void initView() {
        edSearch = findViewById(R.id.ed_search);
        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 1) {
                    model.searchContacts(s.toString());
                }
            }
        });

        rvUsers = findViewById(R.id.recycler_view);
        rvUsers.setLayoutManager(new LinearLayoutManager(this));
        rvUsers.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        rvUsers.setAdapter(adapter);
    }

    private static final String TAG = "SearchContactsActivity";

    private void initData() {

        model.getSearchData().observe(this, listResource -> {
            if (listResource == null||!listResource.isSuccess()) {
                Log.e(TAG, "initData: ");
            } else if (listResource.isSuccess()) {
//                Log.d(TAG, "initData: Success"+listResource.data.size());
                adapter.setNewData(listResource.data);
            }
        });


    }
}
