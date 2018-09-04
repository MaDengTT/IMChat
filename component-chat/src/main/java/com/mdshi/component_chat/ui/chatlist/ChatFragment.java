package com.mdshi.component_chat.ui.chatlist;

import android.arch.lifecycle.ViewModelProvider;
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
import com.mdshi.component_chat.di.component.DaggerChatComponent;
import com.mdshi.component_chat.di.module.ChatModule;
import com.mdshi.component_chat.ui.ChatActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by MaDeng on 2018/8/31.
 */
public class ChatFragment extends BaseFragment implements ChatListContract.View{

    private View rootView;

    private RecyclerView rvList;

    @Inject
    ChatListContract.Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.chat_fragment_chat,container,false);
        initView();
        ChatModule chatModule = new ChatModule();
        chatModule.setView(this);
        DaggerChatComponent.builder().appComponent(getAppComponent()).chatModule(chatModule).build();
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

    @Override
    public void dataToView(List<ChatBean> data) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {

    }
}
