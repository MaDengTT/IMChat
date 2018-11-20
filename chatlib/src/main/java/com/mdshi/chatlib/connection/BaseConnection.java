package com.mdshi.chatlib.connection;

import com.mdshi.chatlib.Bean.SendMessage;
import com.mdshi.chatlib.listener.BaseListener;
import com.mdshi.chatlib.listener.ConnectionListener;
import com.mdshi.chatlib.listener.MessageListener;
import com.mdshi.chatlib.listener.ReceiveListener;
import com.mdshi.chatlib.listener.RequestCallback;

/**
 * Created by MaDeng on 2018/9/3.
 */
public interface BaseConnection {

    void init(Config config);

    void connectionListener(ConnectionListener listener);

    void connect();

    void receiveListener(ReceiveListener listener);

    void sendMessage(SendMessage message);
    void sendMessage(SendMessage message,RequestCallback<Void> listener);

    void sub(String sub, RequestCallback<String> callback);

    void unSub(RequestCallback<String> callback);
}
