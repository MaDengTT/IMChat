package com.mdshi.im.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.mdshi.common.base.BaseActivity;
import com.mdshi.component_chat.ui.ChatFragment;
import com.mdshi.component_chat.ui.contacts.ContactsFragment;
import com.mdshi.im.R;
import com.mdshi.im.ui.my.MyFragment;

public class NavigationActivity extends BaseActivity {

    private BottomNavigationView navigation;

    public static void start(Context context) {
        Intent starter = new Intent(context, NavigationActivity.class);
        context.startActivity(starter);
    }

    FrameLayout flFragment;

    ChatFragment chatFragment;
    ContactsFragment contactsFragment;
    MyFragment myFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        changeFragment(chatFragment);
                        return true;
                    case R.id.navigation_dashboard:
                        changeFragment(contactsFragment);
                        return true;
                    case R.id.navigation_notifications:
                        changeFragment(myFragment);
                        return true;
                }
                return false;
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        initView();
        initFragment();

        changeFragment(chatFragment);
    }

    private void initFragment() {
        chatFragment = new ChatFragment();
        contactsFragment = ContactsFragment.newInstance("", "");
        myFragment = new MyFragment();
        currentFragment = null;
    }

    private void initView() {
        flFragment = findViewById(R.id.fl_fragment);
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    Fragment currentFragment;
    private void changeFragment(Fragment fragment) {

        if (currentFragment == fragment) {
            return;
        }
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();

        if (!fragment.isAdded()) {
            fragmentTransaction.add(R.id.fl_fragment,fragment);
        }
        if (currentFragment == null) {
            fragmentTransaction.show(fragment).commit();
        }else {
            fragmentTransaction.hide(currentFragment).show(fragment).commit();
        }
        currentFragment = fragment;
    }

}
