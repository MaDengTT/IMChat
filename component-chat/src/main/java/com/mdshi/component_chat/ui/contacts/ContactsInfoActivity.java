package com.mdshi.component_chat.ui.contacts;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.TabLayout;
import android.support.transition.TransitionManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
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
    private TabLayout tbLayout;
    private ViewPager vpPage;
    private long session_id;

    ConstraintLayout clContent;

    private ConstraintSet constraintSet1 = new ConstraintSet();
    private ConstraintSet constraintSet2 = new ConstraintSet();

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

    private boolean change = true;
    private void initView() {
        ivAvatar = findViewById(R.id.iv_avatar);
        tvInfo = findViewById(R.id.tv_info);
        tvName = findViewById(R.id.tv_name);
        butSend = findViewById(R.id.but_send);

        tbLayout = findViewById(R.id.tl_title);
        vpPage = findViewById(R.id.vp_page);
        vpPage.setAdapter(new MyPageAdapter(getSupportFragmentManager()));
        tbLayout.setupWithViewPager(vpPage);

        butSend.setOnClickListener(v->ChatActivity.start(this,session_id,id));

        clContent = findViewById(R.id.cl_content);

        constraintSet1.clone(clContent);
        constraintSet2.clone(this,R.layout.chat_contacts_info_2);

        findViewById(R.id.but_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (change) {
                    onApplyTransition();
                }else {
                    onResetTransition();
                }
                change =!change;
            }
        });
    }
    public void onApplyTransition() {
        TransitionManager.beginDelayedTransition(clContent);
        constraintSet2.applyTo(clContent);
    }

    public void onResetTransition() {
        TransitionManager.beginDelayedTransition(clContent);
        constraintSet1.applyTo(clContent);
    }

    private void initData() {
        model.getContact(id).observe(this, contactsEntity -> {
            session_id = contactsEntity.getSession_id();
            loader.loadImaToIv(new AvatarConfig(ivAvatar, contactsEntity.info.avatar));
            tvName.setText(TextUtils.isEmpty(contactsEntity.contactsName)?contactsEntity.info.userName:contactsEntity.contactsName);
            tvInfo.setText(contactsEntity.contactsId+"");
        });
    }

    public class MyPageAdapter extends FragmentPagerAdapter{

        public MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new Fragment();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return position == 0?"信息":position == 1?"其他":"其他";
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}
