package com.mdshi.common.base;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mdshi.common.R;
import com.mdshi.common.constan.UserData;
import com.mdshi.common.di.component.AppComponent;

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
}
