package com.mdshi.component_chat.ui;


import android.content.Intent;

import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;

import com.mdshi.common.base.BaseActivity;
import com.mdshi.common.constan.UserData;
import com.mdshi.common.db.entity.UserEntity;
import com.mdshi.component_chat.R;
import com.mdshi.component_chat.service.IMChatService;
import com.mdshi.component_chat.ui.contacts.ContactsFragment;

import javax.inject.Inject;

public class MainChatActivity extends BaseActivity {

    FrameLayout layout;

    Button add,update,accept,butSwitch;
    private ChatFragment chatFragment;
    private ContactsFragment contactsFragment;

    int page = 0;

    @Inject
    UserData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity_main_chat);
        initView();
        setFragment();
        startService(new Intent(this, IMChatService.class));
    }

    private void setFragment() {
        chatFragment = new ChatFragment();
        contactsFragment = ContactsFragment.newInstance("","");
        getSupportFragmentManager().beginTransaction().add(R.id.framelayout,contactsFragment).hide(contactsFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.framelayout, chatFragment).commit();
    }

    private void initView() {
        layout = findViewById(R.id.framelayout);
        add = findViewById(R.id.but_add);
        update = findViewById(R.id.but_update);
        accept = findViewById(R.id.but_accept);

        findViewById(R.id.but_switch)
                .setOnClickListener(v -> {
                    if (page % 2 == 0) {
                        getSupportFragmentManager().beginTransaction().hide(contactsFragment).show(chatFragment).commit();
                    }else {
                        getSupportFragmentManager().beginTransaction().hide(chatFragment).show(contactsFragment).commit();
                    }
                    page++;
                });

        accept.setOnClickListener(v->{
//            ChatBean bean = new ChatBean();
//            bean.date = new Date();
//            bean.type = ChatBean.Type.TEXT_R;
//            bean.content = "receive Message";
//            bean.session_id = SystemClock.currentThreadTimeMillis();
//            ChatManager.getIns().receive(bean);
            new Thread(() -> {
                UserEntity userEntity = new UserEntity();
                userEntity.userId = 123456;
                data.postValue(userEntity);
            }).start();

        });
    }
}
