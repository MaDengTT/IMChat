package com.mdshi.chatlib.service;

import com.mdshi.chatlib.Bean.Message;
import com.mdshi.chatlib.Bean.MessageBean;
import com.mdshi.chatlib.Bean.SendMessage;
import com.mdshi.chatlib.Bean.TextMsg;
import com.mdshi.chatlib.connection.BaseConnection;
import com.mdshi.chatlib.listener.AdoptableFuture;
import com.mdshi.chatlib.listener.MessageListener;
import com.mdshi.chatlib.listener.Promise;
import com.mdshi.chatlib.listener.ReceiveListener;
import com.mdshi.chatlib.listener.RequestCallback;

import java.util.ArrayList;
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
            public void onReceiveMessage(String key, String message) {
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

    public AdoptableFuture<Void> sendMessage(final MessageBean bean) {
        final Promise<Void> future = new Promise<>();
        SendMessage message = new SendMessage();
        message.key = "/IM/Chat/000";
        message.body = bean.key+bean.message;
        getConnection().sendMessage(message, future);
        return future;
    }

    public AdoptableFuture<TextMsg> sendMessage(final TextMsg msg) {
        final Promise<TextMsg> future = new Promise<>();
        SendMessage message = new SendMessage();
        message.key = "/IM/Chat/000";
        message.body = msg.from+msg.getContentText();
        getConnection().sendMessage(message, new RequestCallback<Void>() {
            @Override
            public void onSuccess(Void param) {
                msg.status = Message.MsgStatus.success;
                future.onSuccess(msg);
            }

            @Override
            public void onFailed(int code) {
                msg.status = Message.MsgStatus.fail;
                future.onFailed(code);
            }

            @Override
            public void onException(Throwable exception) {
                future.onException(exception);
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
