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

public class NavigationActivity extends BaseActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, NavigationActivity.class);
        context.startActivity(starter);
    }

    FrameLayout flFragment;

    ChatFragment chatFragment;
    ContactsFragment contactsFragment;

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
    //                    mTextMessage.setText(R.string.title_notifications);
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
    }

    private void initFragment() {
        chatFragment = new ChatFragment();
        contactsFragment = ContactsFragment.newInstance("", "");

        currentFragment = chatFragment;
    }

    private void initView() {
        flFragment = findViewById(R.id.fl_fragment);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
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
        fragmentTransaction.hide(currentFragment).show(fragment).commit();
        currentFragment = fragment;
    }

}
