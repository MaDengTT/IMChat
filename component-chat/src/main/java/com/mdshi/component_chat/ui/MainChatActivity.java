package com.mdshi.component_chat.ui;

import android.os.SystemClock;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;

import com.mdshi.component_chat.ChatManager;
import com.mdshi.component_chat.R;
import com.mdshi.component_chat.bean.ChatBean;

import java.util.Date;

public class MainChatActivity extends AppCompatActivity {

    FrameLayout layout;

    Button add,update,accept;
    private ChatFragment chatFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity_main_chat);
        initView();
        setFragment();
    }

    private void setFragment() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();

        chatFragment = new ChatFragment();

        fragmentTransaction.add(R.id.framelayout, chatFragment).commit();
    }

    private void initView() {
        layout = findViewById(R.id.framelayout);
        add = findViewById(R.id.but_add);
        update = findViewById(R.id.but_update);
        accept = findViewById(R.id.but_accept);

        add.setOnClickListener(v -> chatFragment.addTest());
        update.setOnClickListener(v->chatFragment.updateTest());
        accept.setOnClickListener(v->{
            ChatBean bean = new ChatBean();
            bean.date = new Date();
            bean.type = ChatBean.Type.TEXT_R;
            bean.content = "receive Message";
            bean.session_id = SystemClock.currentThreadTimeMillis();
            ChatManager.getIns().receive(bean);
        });
    }
}
