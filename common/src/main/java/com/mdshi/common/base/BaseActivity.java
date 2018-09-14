package com.mdshi.common.base;

import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.mdshi.common.di.component.AppComponent;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by MaDeng on 2018/8/31.
 */
public class BaseActivity extends AppCompatActivity{

    CompositeDisposable cDisposable;

    protected void addDisposable(Disposable disposable) {
        if (cDisposable == null) {
            cDisposable = new CompositeDisposable();
        }
        cDisposable.add(disposable);
    }

    protected AppComponent getAppComponent(){
        if(getApplication() instanceof BaseApplication){
            return ((BaseApplication)getApplication()).AppComponent();
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
}
