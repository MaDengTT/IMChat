package com.mdshi.component_chat.ui;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.SystemClock;
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
import com.mdshi.common.db.entity.MessageListEntity;
import com.mdshi.component_chat.ChatManager;
import com.mdshi.component_chat.R;
import com.mdshi.component_chat.adapter.ChatItemAdapter;
import com.mdshi.component_chat.di.component.DaggerChatComponent;
import com.mdshi.component_chat.ui.chat.ChatActivity;


import java.util.Date;

import javax.inject.Inject;



/**
 * Created by MaDeng on 2018/8/31.
 */
public class ChatFragment extends BaseFragment{

    private View rootView;

    private RecyclerView rvList;

    public ChatModel chatModel;

    @Inject
    ViewModelProvider.Factory factory;
    @Inject
    ChatItemAdapter adapter;

    private static final String TAG = "ChatFragment";
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//
//        if (factory == null) {
//            Log.e(TAG, "onActivityCreated: is NULL" );
//        }else {
//            Log.e(TAG, "onActivityCreated: not null");
//        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.chat_fragment_chat,container,false);
        chatModel = ViewModelProviders.of(this, factory).get(ChatModel.class);
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
                toActivity((MessageListEntity) adapter.getItem(position));
            }
        });
        rvList.setAdapter(adapter);
    }

    private void initData() {
//        DaggerChatComponent.builder().appComponent(getAppComponent()).build().inject(this);

        chatModel.getChatList().observe(this, data -> adapter.setNewData(data));


    }

    @Override
    public void onResume() {
        super.onResume();
        ChatManager.getIns().registerListListener(bean -> true);
    }

    @Override
    public void onPause() {
        super.onPause();
        ChatManager.getIns().unregisterChatListener(bean -> false);
    }

    public void toActivity(MessageListEntity entity) {
        entity.unReadNum = 0;
        chatModel.updateChatValue(entity);
        if (entity.sessionType == 0) {
            ChatActivity.start(getActivity(),entity.id,entity.other_Id);
        }
    }

    public void addTest() {
        MessageListEntity entity = new MessageListEntity();
        entity.id = SystemClock.currentThreadTimeMillis();
        entity.user_Id = 123456;
        entity.newDate = new Date();
        chatModel.addChatValue(entity);
    }

    public void updateTest() {
        MessageListEntity messageListEntity = adapter.getData().get(1);
        messageListEntity.unReadNum++;
        messageListEntity.newDate = new Date();
        chatModel.updateChatValue(messageListEntity);
    }


}
