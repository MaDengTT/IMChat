package com.mdshi.component_chat.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mdshi.component_chat.R;

public class ChatActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, ChatActivity.class);
//        starter.putExtra();
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity_chat);
    }
}
