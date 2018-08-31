package com.mdshi.common.base;

import android.support.v7.app.AppCompatActivity;

import com.mdshi.common.di.component.AppComponent;

/**
 * Created by MaDeng on 2018/8/31.
 */
public class BaseActivity extends AppCompatActivity{
    protected AppComponent getAppComponent(){
        if(getApplication() instanceof BaseApplication){
            return ((BaseApplication)getApplication()).AppComponent();
        }else {
            return null;
        }
    }
}
