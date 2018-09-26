package com.mdshi.chatlib;

import com.mdshi.chatlib.Bean.MessageBean;
import com.mdshi.chatlib.Bean.SendMessage;
import com.mdshi.chatlib.connection.BaseConnection;
import com.mdshi.chatlib.connection.Config;
import com.mdshi.chatlib.connection.Mqtt_Connection;
import com.mdshi.chatlib.listener.ConnectionListener;
import com.mdshi.chatlib.listener.IMListener;
import com.mdshi.chatlib.listener.MessageListener;
import com.mdshi.chatlib.listener.ReceiveListener;
import com.mdshi.chatlib.listener.SendMessageListener;

import java.security.Key;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by MaDeng on 2018/8/31.
 */
public class IMChat {

    private static IMChat ins;
//    private MessageListener messagelistener;

    Set<MessageListener> messageListeners;

    private BaseConnection connection;


    private String iMtopic = "/IM/Chat/";
    private IMListener IMListener;
    private String key;


    private IMChat(){

    }

    private IMChat(final BaseConnection connection){
        this.connection = connection;
        connection.receiveListener(new ReceiveListener() {
            @Override
            public void onReciveMessage(String key, String message) {
                Debug.d("reciveMessage:"+key+":"+message);
                if (messageListeners != null) {
                    for (MessageListener listener:messageListeners) {
                        listener.message(message);
                    }
                }
            }

            @Override
            public void onSuccess() {
                Debug.d("reciveMessage: success");
                if (messageListeners != null) {
                    for (MessageListener listener:messageListeners) {
                        listener.onSuccess();
                    }
                }
            }

            @Override
            public void onFailure(Throwable value) {
                Debug.e("reciveMessage:onFailure"+value.toString());
                if (messageListeners != null) {
                    for (MessageListener listener:messageListeners) {
                        listener.onFailure(value);
                    }
                }
            }
        });
        connection.connectionListener(new ConnectionListener() {
            @Override
            public void onStart() {
                Debug.d("connectionListener:onStart");
            }

            @Override
            public void onSuccess() {
                Debug.d("connectionListener:onSuccess");
                connection.sub(iMtopic+key);
                if (IMListener != null) {
                    IMListener.onSuccess();
                }
            }

            @Override
            public void onFailure(Throwable value) {
                Debug.e("connectionListener:onFailure"+value.toString());
                if (IMListener != null) {
                    IMListener.onFailure(value);
                }
            }
        });

    }

    public static void init(String key){
        Config config = new Config();
        config.CONNECTION_STRING = "www.mdshi.cn";
        config.key = key;
        ins = new IMChat(new Mqtt_Connection(config));
        ins.key = key;
//        String topics = "/IM/Chat/"+key;
//        Topic topic = new Topic(topics, QoS.AT_LEAST_ONCE);
//        ins.topics = new Topic[]{topic};
//        ins.connection = new Mqtt_Connection();
    }

    public static void connect() {
        ins.connection.connect();
    }

    public static void addMessageListener(final MessageListener listener) {

        if (ins == null) {
            throw new NullPointerException("IMChat is not init!");
        }
        if (ins.messageListeners == null) {
            ins.messageListeners = new LinkedHashSet<>();
        }
        ins.messageListeners.add(listener);
    }

    public static void removeMessageListener(final MessageListener listener) {
        if (ins == null) {
            throw new NullPointerException("IMChat is not init!");
        }
        ins.messageListeners.remove(listener);
    }

    public static void setIMListener(final IMListener listener) {
        if (ins == null) {
            throw new NullPointerException("IMChat is not init!");
        }
        ins.IMListener = listener;

    }

    public static void sendMessage(MessageBean bean, final SendMessageListener listener){
        if (ins == null) {
            throw new NullPointerException("IMChat is not init!");
        }
        SendMessage message = new SendMessage();
        message.key = "";
        message.body = bean.message;
        ins.connection.sendMessage(message);
    }


}
