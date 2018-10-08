package com.mdshi.chatlib.listener;

import com.mdshi.chatlib.Bean.MessageBean;

/**
 * Created by MaDeng on 2018/8/31.
 */
public interface MessageListener  extends BaseListener{

    void message(String data);
    void messageTBean(MessageBean bean);
}
