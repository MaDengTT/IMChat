package com.mdshi.component_chat.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;

import com.mdshi.component_chat.R;
import com.mdshi.component_chat.adapter.ChatMessageAdapter;
import com.mdshi.component_chat.bean.ChatBean;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    RecyclerView chatListView;
    ChatMessageAdapter adapter;

    EditText edChat;
    Button butSend;
    public static void start(Context context) {
        Intent starter = new Intent(context, ChatActivity.class);
//        starter.putExtra();
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity_chat);
        initView();
        setData();
    }

    private void setData() {
        List<ChatBean> list = new ArrayList<>();
        String s = "测试数据12345abcde";
        String s1 = "测试数据12345abcde";
        for(int i=0;i<20;i++) {
            ChatBean bean = new ChatBean();
            bean.content = s;
            s=s+s1;
            if (i % 2 == 0) {
                bean.type = ChatBean.Type.TEXT_L;
            }else {
                bean.type = ChatBean.Type.TEXT_R;
            }
            list.add(bean);
        }
        adapter.setNewData(list);
    }

    public void addChatMessage(ChatBean newMsg) {
        adapter.addData(0,newMsg);
        //TODO 判断是否是正在聊天或者在查看消息，接受到的消息是否要滑动到最底层

        chatListView.smoothScrollToPosition(0);
    }

    private void initView() {
        chatListView = findViewById(R.id.rv_chat_list);
        edChat = findViewById(R.id.ed_chat);
        butSend = findViewById(R.id.but_send);

        butSend.setOnClickListener(v -> {
        });

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        llm.setReverseLayout(true);
        chatListView.setLayoutManager(llm);
        adapter = new ChatMessageAdapter(null);
        chatListView.setAdapter(adapter);
    }


}
