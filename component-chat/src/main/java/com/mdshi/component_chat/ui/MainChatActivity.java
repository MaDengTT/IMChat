package com.mdshi.component_chat.ui;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.mdshi.component_chat.R;
import com.mdshi.component_chat.ui.chatlist.ChatFragment;

public class MainChatActivity extends AppCompatActivity {

    FrameLayout layout;

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

        ChatFragment chatFragment = new ChatFragment();

        fragmentTransaction.add(R.id.framelayout, chatFragment).commit();
    }

    private void initView() {
        layout = findViewById(R.id.framelayout);
    }
}
