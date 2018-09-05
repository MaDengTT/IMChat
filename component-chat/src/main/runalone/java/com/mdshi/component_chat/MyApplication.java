package com.mdshi.component_chat;

//import com.mdshi.chatlib.IMChat;
import com.mdshi.common.base.BaseApplication;
import com.mdshi.common.di.component.AppComponent;
import com.mdshi.common.di.component.DaggerAppComponent;

/**
 * Created by MaDeng on 2018/9/3.
 */
public class MyApplication extends BaseApplication {

    private AppComponent appComponent;
    private String IMKEY="";

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder().application(this).build();
        ininIMChat();
    }

    private void ininIMChat() {
//        IMChat.init(IMKEY);
    }

    @Override
    public AppComponent AppComponent() {
        return appComponent;
    }
}
