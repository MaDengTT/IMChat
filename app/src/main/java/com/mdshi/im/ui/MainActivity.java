package com.mdshi.im.ui;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import com.mdshi.common.base.BaseActivity;

import com.mdshi.common.constan.UserData;
import com.mdshi.im.R;
import com.mdshi.im.ui.userui.LoginActivity;


import javax.inject.Inject;

public class MainActivity extends BaseActivity {


    @Inject
    UserData user;

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user.observe(this, entity -> {
            if (entity != null && entity.userID != 0) {
                NavigationActivity.start(this);
                finish();
            }else {
                LoginActivity.start(this);
                finish();
            }
        });
    }
}
