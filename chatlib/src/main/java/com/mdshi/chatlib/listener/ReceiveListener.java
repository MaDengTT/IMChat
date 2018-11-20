package com.mdshi.chatlib.listener;

import com.mdshi.chatlib.Bean.MessageBean;

/**
 * Created by MaDeng on 2018/9/3.
 */
public interface ReceiveListener extends BaseListener{

    void onReceiveMessage(String key, String message);

}
