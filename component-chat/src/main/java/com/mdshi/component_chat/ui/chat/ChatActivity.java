package com.mdshi.component_chat.ui.chat;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.loadmore.SimpleLoadMoreView;
import com.mdshi.common.base.BaseActivity;

import com.mdshi.component_chat.ChatManager;
import com.mdshi.component_chat.R;
import com.mdshi.component_chat.adapter.ChatMessageAdapter;
import com.mdshi.component_chat.bean.ChatBean;
import com.mdshi.component_chat.listener.ChatListener;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class ChatActivity extends BaseActivity {

    RecyclerView chatListView;
    ChatMessageAdapter adapter;

    EditText edChat;
    Button butSend;

    @Inject
    ViewModelProvider.Factory factory;

    ChatActivityModel model;

    private long session_id;

    public static void start(Context context,long session_id) {
        Intent starter = new Intent(context, ChatActivity.class);
        starter.putExtra("session_id",session_id);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity_chat);
        model = ViewModelProviders.of(this, factory).get(ChatActivityModel.class);
        session_id = getIntent().getLongExtra("session_id", 0);
        Log.d(TAG, "session_id"+session_id);
        initView();
        initData();
    }

    private void initData() {
        model.getData().observe(this, chatBeans -> {
            if(adapter.isLoading()){adapter.loadMoreComplete();}
            adapter.addData(chatBeans);
            if (chatBeans==null||chatBeans.size() == 0) {
                adapter.loadMoreEnd();
            }
        });
        model.setSessionId(session_id);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ChatManager.getIns().registerChatListener(bean -> {
            if (bean.session_id == session_id) {
                addChatMessage(bean);
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onPause() {
        ChatManager.getIns().unregisterChatListener(null);
        super.onPause();
    }

    public void addChatMessage(ChatBean newMsg) {
        adapter.addData(0,newMsg);
        //TODO 判断是否是正在聊天或者在查看消息，接受到的消息是否要滑动到最底层
        chatListView.smoothScrollToPosition(0);
    }

    private static final String TAG = "ChatActivity";
    private void initView() {
        chatListView = findViewById(R.id.rv_chat_list);
        edChat = findViewById(R.id.ed_chat);
        butSend = findViewById(R.id.but_send);

        butSend.setOnClickListener(v -> {
            ChatBean bean = new ChatBean();
            bean.date = new Date();
            bean.type = ChatBean.Type.TEXT_R;
            bean.content = edChat.getText().toString();
            bean.session_id = session_id;
            model.addMsgChatData(bean);
        });

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        llm.setReverseLayout(true);
        chatListView.setLayoutManager(llm);
        adapter = new ChatMessageAdapter(null);
        adapter.setLoadMoreView(new SimpleLoadMoreView());
        adapter.setOnLoadMoreListener(() -> model.next(adapter.getData()==null?0:adapter.getData().size()),chatListView);
        chatListView.setAdapter(adapter);
    }

}