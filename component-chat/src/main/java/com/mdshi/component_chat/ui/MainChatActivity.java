package com.mdshi.component_chat.ui;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;

import com.mdshi.component_chat.R;

public class MainChatActivity extends AppCompatActivity {

    FrameLayout layout;

    Button add,update;
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

        add.setOnClickListener(v -> chatFragment.addTest());
        update.setOnClickListener(v->chatFragment.updateTest());
    }
}
