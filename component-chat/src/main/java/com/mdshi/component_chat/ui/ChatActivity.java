package com.mdshi.component_chat.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.mdshi.common.base.BaseActivity;

import com.mdshi.component_chat.R;
import com.mdshi.component_chat.adapter.ChatMessageAdapter;
import com.mdshi.component_chat.bean.ChatBean;

import com.mdshi.component_chat.ui.chat.ChatActivityModel;

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
    private int pageSize = 10;
    private int pageNo = 0;

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

        Log.d(TAG, "onCreate: "+model.getClass().hashCode());

        session_id = getIntent().getLongExtra("session_id", 0);
        initView();
        initData();
    }

    private void initData() {
//        chatModel.getChatMsgData(session_id, 10, 0)
//                .subscribe(list-> adapter.setNewData(list), error-> Log.e(TAG, "initData: ",error ));
        model.getData(session_id)
                .observe(this,data->addChatMessage(data));
        model.getData(session_id,pageSize,pageNo)
                .observe(this,datalist->setData(datalist));
    }

    private void setData(List<ChatBean> list) {
        if (pageNo == 0) {
            adapter.setNewData(list);
        }else {
            adapter.addData(list);
        }
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
        chatListView.setAdapter(adapter);
    }

}
