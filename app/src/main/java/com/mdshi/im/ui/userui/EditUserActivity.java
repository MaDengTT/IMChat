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
import com.mdshi.im.utils.Glide4Engine;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.ArrayList;

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
    private static final int REQUEST_CODE_CHOOSE = 23;

    public static void start(Context context) {
        Intent starter = new Intent(context, EditUserActivity.class);
        context.startActivity(starter);
    }

    @Inject
    ViewModelProvider.Factory factory;

    final String[] permissionNames = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

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
                PermissionRequest.getInstance(this).requestPermission(new PermissionRequest.PermissionListener() {
                    @Override
                    public void permissionGranted() {
                        putImage();
                    }

                    @Override
                    public void permissionDenied(ArrayList<String> permissions) {

                    }

                    @Override
                    public void permissionNeverAsk(ArrayList<String> permissions) {

                    }
                }, permissionNames);

                break;
            case R.id.rl_avatar:
                break;
        }
    }

    private void putImage() {
        Matisse.from(this)
                .choose(MimeType.ofAll(), false)
                .countable(true)
                .captureStrategy(new CaptureStrategy(true, "com.mdshi.im.fileprovider"))
                .maxSelectable(1)
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .imageEngine(new Glide4Engine())
                .forResult(REQUEST_CODE_CHOOSE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
//            mAdapter.setData(Matisse.obtainResult(data), Matisse.obtainPathResult(data));
//            Log.d("OnActivityResult", "onActivityResult: "+Matisse.obtainPathResult(data).toString());
            Log.e("OnActivityResult ", String.valueOf(Matisse.obtainOriginalState(data)));
            if (Matisse.obtainPathResult(data) != null) {
                userModel.setUserImage(Matisse.obtainPathResult(data).get(0));
            }
        }
    }
}
