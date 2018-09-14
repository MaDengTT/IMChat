package com.mdshi.im.ui;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.mdshi.common.base.BaseActivity;
import com.mdshi.common.base.BaseBean;
import com.mdshi.common.constan.UserData;
import com.mdshi.common.db.entity.UserEntity;
import com.mdshi.im.R;
import com.mdshi.im.di.component.DaggerMainComponent;
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
        DaggerMainComponent.builder().appComponent(getAppComponent()).build().inject(this);
        user.observe(this, entity -> {
            Log.d(TAG, "onCreate: "+entity.userName);
        });
    }
}
