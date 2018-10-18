package com.mdshi.im.ui.userui;

import android.Manifest;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mdshi.common.base.BaseActivity;
import com.mdshi.common.constan.UserData;
import com.mdshi.common.image.AvatarConfig;
import com.mdshi.common.image.ImageLoader;
import com.mdshi.common.utils.PermissionRequest;
import com.mdshi.im.R;
import com.mdshi.common.image.glide.Glide4Engine;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditUserActivity extends BaseActivity {

    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.ed_name)
    EditText edName;
    private UserModel userModel;

    @Inject
    UserData userData;

    @Inject
    ImageLoader loader;


    public static void start(Context context) {
        Intent starter = new Intent(context, EditUserActivity.class);
        context.startActivity(starter);
    }

    @Inject
    ViewModelProvider.Factory factory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        ButterKnife.bind(this);
        userModel = ViewModelProviders.of(this, factory).get(UserModel.class);
        initView();
        initData();
    }

    private void initData() {
        userData.observe(this, userEntity -> {
            AvatarConfig config = new AvatarConfig(ivAvatar, userEntity.avatar);
            loader.loadImaToIv(config);
            edName.setText(userEntity.userName);
        });
    }

    private void initView() {
    }

    @OnClick({R.id.iv_avatar, R.id.rl_avatar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_avatar:
                putImage(1);
                break;
            case R.id.rl_avatar:
                break;
        }
    }


    @Override
    protected void onImageData(List<String> data) {
        userModel.setUserImage(data.get(0));
    }
}
