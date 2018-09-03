package com.mdshi.component_chat.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.mdshi.common.base.BaseFragment;
import com.mdshi.component_chat.R;
import com.mdshi.component_chat.adapter.ChatItemAdapter;
import com.mdshi.component_chat.bean.ChatBean;

import java.util.ArrayList;
import java.util.List;

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
        initData();
        return rootView;
    }

    private void initView() {
        rvList = rootView.findViewById(R.id.rv_list);

        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rvList.addItemDecoration(decoration);
        rvList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                ChatActivity.start(getActivity());
            }
        });
    }

    private void initData() {
        List<ChatBean> data = new ArrayList<>();
        for(int i = 0;i<10;i++) {
            data.add(new ChatBean());
        }
        rvList.setAdapter(new ChatItemAdapter(data));
    }
}
