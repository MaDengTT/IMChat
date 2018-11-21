package com.mdshi.chatlib.service;

import com.mdshi.chatlib.Bean.Message;
import com.mdshi.chatlib.Bean.MessageBean;
import com.mdshi.chatlib.Bean.SendMessage;
import com.mdshi.chatlib.Bean.TextMsg;
import com.mdshi.chatlib.connection.BaseConnection;
import com.mdshi.chatlib.listener.AdoptableFuture;
import com.mdshi.chatlib.listener.Function;
import com.mdshi.chatlib.listener.MessageListener;
import com.mdshi.chatlib.listener.Promise;
import com.mdshi.chatlib.listener.ReceiveListener;
import com.mdshi.chatlib.listener.RequestCallback;
import com.mdshi.chatlib.listener.Transformations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.FutureTask;

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
        getConnection().sendMessage(message, Transformations.map(future, new Function<Void, TextMsg>() {
            @Override
            public TextMsg apply(Void aVoid) {
                msg.status = TextMsg.MsgStatus.success;
                return msg;
            }
        }));
        return future;
    }

    public void addMessageListener(MessageListener listener) {
        listeners.add(listener);
    }

    public void removeMessageListener(MessageListener listener) {
        listeners.remove(listener);
    }
}
