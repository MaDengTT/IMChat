package com.mdshi.component_chat.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.mdshi.common.db.dao.MessageDao;
import com.mdshi.common.db.entity.MessageEntity;
import com.mdshi.common.db.entity.MessageListEntity;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class IMReceiver extends BroadcastReceiver {




    @Override
    public void onReceive(Context context, Intent intent) {

    }

}
