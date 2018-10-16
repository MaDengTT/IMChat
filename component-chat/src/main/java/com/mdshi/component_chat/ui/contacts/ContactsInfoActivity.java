package com.mdshi.component_chat.ui.contacts;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mdshi.common.base.BaseActivity;
import com.mdshi.common.image.AvatarConfig;
import com.mdshi.common.image.ImageLoader;
import com.mdshi.component_chat.R;
import com.mdshi.component_chat.ui.chat.ChatActivity;

import javax.inject.Inject;

public class ContactsInfoActivity extends BaseActivity {


    @Inject
    ImageLoader loader;
    @Inject
    ViewModelProvider.Factory factory;
    private ContactsModel model;
    private long id;

    private ImageView ivAvatar;
    private TextView tvName,tvInfo;
    private Button butSend;
    private long session_id;

    public static void start(Context context,long id) {
        Intent starter = new Intent(context, ContactsInfoActivity.class);
        starter.putExtra("id",id);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity_contacts_info);
        model = ViewModelProviders.of(this, factory).get(ContactsModel.class);
        id = getIntent().getLongExtra("id", 0);

        initView();
        initData();
    }

    private void initView() {
        ivAvatar = findViewById(R.id.iv_avatar);
        tvInfo = findViewById(R.id.tv_info);
        tvName = findViewById(R.id.tv_name);
        butSend = findViewById(R.id.but_send);
        butSend.setOnClickListener(v->ChatActivity.start(this,session_id,id));
    }

    private void initData() {
        model.getContact(id).observe(this, contactsEntity -> {
            session_id = contactsEntity.getSession_id();
            loader.loadImaToIv(new AvatarConfig(ivAvatar, contactsEntity.info.avatar));
            tvName.setText(TextUtils.isEmpty(contactsEntity.contactsName)?contactsEntity.info.userName:contactsEntity.contactsName);
            tvInfo.setText(contactsEntity.contactsId+"");
        });
    }
}
