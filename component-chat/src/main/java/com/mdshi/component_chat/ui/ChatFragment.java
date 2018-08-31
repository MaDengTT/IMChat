package com.mdshi.component_chat.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mdshi.common.base.BaseFragment;
import com.mdshi.component_chat.R;

/**
 * Created by MaDeng on 2018/8/31.
 */
public class ChatFragment extends BaseFragment{

    private View rootView;

    private RecyclerView rvList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.chat_fragment_chat,container,false);
        initView();
        return rootView;
    }

    private void initView() {
        rvList = rootView.findViewById(R.id.rv_list);
    }
}
