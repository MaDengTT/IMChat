package com.mdshi.common.base;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mdshi.common.R;
import com.mdshi.common.constan.UserData;
import com.mdshi.common.di.component.AppComponent;
import com.mdshi.common.image.glide.Glide4Engine;
import com.mdshi.common.utils.PermissionRequest;
import com.mdshi.common.utils.PermissionUtils;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.HasSupportFragmentInjector;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by MaDeng on 2018/8/31.
 */
public class BaseActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    final String[] permissionNames = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    private static final int REQUEST_CODE_CHOOSE = 23;

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    CompositeDisposable cDisposable;

    protected void addDisposable(Disposable disposable) {
        if (cDisposable == null) {
            cDisposable = new CompositeDisposable();
        }
        cDisposable.add(disposable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent("com.mdshi.component_chat.imreceiver");
        intent.addCategory("android.intent.category.DEFAULT");
        sendBroadcast(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitleName(getTitle().toString());
    }

    protected AppComponent getAppComponent(){
        if(getApplication() instanceof BaseApplication){
            return ((BaseApplication)getApplication()).appComponent();
        }else {
            return null;
        }
    }
    protected UserData getUserData(){
        if(getApplication() instanceof BaseApplication){
            return ((BaseApplication)getApplication()).appComponent().userData();
        }else {
            return null;
        }
    }

    protected void toast(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        if (cDisposable != null && !cDisposable.isDisposed()) {
            cDisposable.clear();
        }
        super.onDestroy();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    protected void setTitleName(String titleName) {
        TextView titleView = findViewById(R.id.tv_title);
        if (titleView != null) {
            titleView.setText(titleName);
        }
    }

    protected void putImage(int imageSize) {
        if(PermissionUtils.hasPermission(this,permissionNames)){
            startImage(imageSize);
        }else {
            PermissionRequest.getInstance(this).requestPermission(new PermissionRequest.PermissionListener() {
                @Override
                public void permissionGranted() {
                    startImage(imageSize);
                }

                @Override
                public void permissionDenied(ArrayList<String> permissions) {

                }

                @Override
                public void permissionNeverAsk(ArrayList<String> permissions) {

                }
            }, permissionNames);
        }


    }

    private void startImage(int imageSize) {
        Matisse.from(this)
                .choose(MimeType.ofAll(), false)
                .countable(true)
                .captureStrategy(new CaptureStrategy(true, "com.mdshi.im.fileprovider"))
                .maxSelectable(imageSize)
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .imageEngine(new Glide4Engine())
                .forResult(REQUEST_CODE_CHOOSE);
    }
    protected void onImageData(List<String> data) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            Log.e("OnActivityResult ", String.valueOf(Matisse.obtainOriginalState(data)));
            if (Matisse.obtainPathResult(data) != null) {
                onImageData(Matisse.obtainPathResult(data));
            }
        }
    }
}
