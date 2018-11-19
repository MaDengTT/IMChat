package com.mdshi.chatlib.service;

import com.mdshi.chatlib.Bean.MessageBean;
import com.mdshi.chatlib.Bean.SendMessage;
import com.mdshi.chatlib.connection.BaseConnection;
import com.mdshi.chatlib.listener.AbortableFuture;
import com.mdshi.chatlib.listener.MessageListener;
import com.mdshi.chatlib.listener.ReceiveListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by MaDeng on 2018/11/19.
 */
public class MessageService extends IMService {

    private List<MessageListener> listeners = new ArrayList<>();

    public MessageService(BaseConnection connection) {
        super(connection);
        connection.receiveListener(new ReceiveListener() {
            @Override
            public void onReciveMessage(String key, String message) {
                if (listeners != null) {
                    for (MessageListener listener:listeners) {
                        listener.message(message);
                        MessageBean bean = new MessageBean(message);
                        listener.messageTBean(bean);
                    }
                }
            }

            @Override
            public void onSuccess() {
                if (listeners != null) {
                    for (MessageListener listener:listeners) {
                        listener.onSuccess();
                    }
                }
            }

            @Override
            public void onFailure(Throwable value) {
                if (listeners != null) {
                    for (MessageListener listener:listeners) {
                        listener.onFailure(value);
                    }
                }
            }
        });
    }

    public AbortableFuture<MessageBean> sendMessage(final MessageBean bean) {
        final Future<MessageBean> future = new Future<>();
        SendMessage message = new SendMessage();
        message.key = "/IM/Chat/000";
        message.body = bean.key+bean.message;
        getConnection().sendMessage(message, new ReceiveListener() {
            @Override
            public void onReciveMessage(String key, String message) {

            }

            @Override
            public void onSuccess() {
                future.postData(bean);
            }

            @Override
            public void onFailure(Throwable value) {
                future.postException(0,value);
            }
        });
        return future;
    }

    public void addMessageListener(MessageListener listener) {
        listeners.add(listener);
    }

    public void removeMessageListener(MessageListener listener) {
        listeners.remove(listener);
    }
}
