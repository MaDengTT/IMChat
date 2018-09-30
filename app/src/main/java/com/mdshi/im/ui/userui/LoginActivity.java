package com.mdshi.im.ui.userui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mdshi.common.base.BaseActivity;
import com.mdshi.common.base.BaseBean;
import com.mdshi.common.db.entity.UserEntity;
import com.mdshi.common.utils.RegexUtils;
import com.mdshi.common.viewmodel.ViewModelFactory;
import com.mdshi.im.R;
import com.mdshi.im.di.component.DaggerMainComponent;
import com.mdshi.im.ui.MainActivity;
import com.mdshi.im.ui.NavigationActivity;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;


public class LoginActivity extends BaseActivity {

    TextView tvRegister;
    Button butLogin;
    EditText edName, edPassword;
    UserModel model;
    @Inject
    ViewModelProvider.Factory factory;

    public static void start(Context context) {
        Intent starter = new Intent(context, LoginActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        model = ViewModelProviders.of(this, factory).get(UserModel.class);
        initView();
    }

    private void initView() {
        butLogin = findViewById(R.id.but_login);
        edName = findViewById(R.id.ed_name);
        edPassword = findViewById(R.id.ed_password);
        tvRegister = findViewById(R.id.tv_register);

        butLogin.setOnClickListener(v -> login(edName.getText().toString().trim(), edPassword.getText().toString()));
//        tvRegister.setOnClickListener(v -> RegisterActivity.start(this));
        tvRegister.setOnClickListener(v-> NavigationActivity.start(this));
    }

    private static final String TAG = "LoginActivity";
    private void login(String name, String password) {
        Flowable<BaseBean<UserEntity>> login = null;

        if (TextUtils.isEmpty(password)) {
            toast("密码不能为空");
            return;
        }

        if (RegexUtils.checkPhone(name)) {
            login = model.login(name, null, password);
        } else if (RegexUtils.checkEmail(name)) {
            login = model.login(null, name, password);
        }else {
            toast("请输入正确的手机号、邮箱");
            return;
        }

        Disposable disposable = login.subscribe(userEntityBaseBean -> {
            Log.d(TAG, "onNext: " + userEntityBaseBean.toString());
            if (userEntityBaseBean.isSuccess()) {
                NavigationActivity.start(this);
            } else {
                toast(userEntityBaseBean.message);
            }
        }, throwable -> Log.e(TAG, "login: ", throwable));
        addDisposable(disposable);
    }
}
