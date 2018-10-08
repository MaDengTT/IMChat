package com.mdshi.im.ui.userui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import android.widget.Button;
import android.widget.EditText;

import com.mdshi.common.base.BaseActivity;
import com.mdshi.common.base.BaseBean;

import com.mdshi.common.db.entity.UserEntity;
import com.mdshi.common.utils.RegexUtils;
import com.mdshi.im.R;


import com.mdshi.im.ui.MainActivity;


import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;

public class RegisterActivity extends BaseActivity {

    private static final String TAG = "RegisterActivity";
    Button butRegister;
    EditText edName,edPassword;

    @Inject
    UserModel model;

    public static void start(Context context) {
        Intent starter = new Intent(context, RegisterActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        initView();
    }

    private void initView() {
        butRegister = findViewById(R.id.but_register);
        edName = findViewById(R.id.ed_name);
        edPassword = findViewById(R.id.ed_password);

        butRegister.setOnClickListener(v -> {
            register(edName.getText().toString().trim(), edPassword.getText().toString());
        });
    }

    private void register(String name, String password) {
        Flowable<BaseBean<UserEntity>> register = null;

        if (TextUtils.isEmpty(password)) {
            toast("密码不能为空");
            return;
        }

        if (RegexUtils.checkPhone(name)) {
            register = model.register(name, null, password);
        } else if (RegexUtils.checkEmail(name)) {
            register = model.register(null, name, password);
        }else {
            toast("请输入正确的手机号、邮箱");
            return;
        }

        Disposable subscribe = register.subscribe(userEntityBaseBean -> {
            if (userEntityBaseBean.isSuccess()) {
                toast("成功创建账户");
                MainActivity.start(this);
                finish();
            }else {
                toast(userEntityBaseBean.message);
            }
        });
        addDisposable(subscribe);
    }
}
