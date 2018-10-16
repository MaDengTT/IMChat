package com.mdshi.im.ui.setting;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.mdshi.common.base.BaseActivity;
import com.mdshi.common.constan.UserData;
import com.mdshi.common.db.entity.UserEntity;
import com.mdshi.im.R;
import com.mdshi.im.ui.userui.LoginActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, SettingActivity.class);
        context.startActivity(starter);
    }

    @Inject
    UserData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        data.observe(this, userEntity -> {
            if (userEntity == null) {
                LoginActivity.start(this);
                finish();
            }
        });
    }

    @OnClick({R.id.but_login_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.but_login_out:
                data.logout();
                break;
        }
    }
}
