package com.mdshi.chatlib;

import com.mdshi.chatlib.Bean.MessageBean;
import com.mdshi.chatlib.listener.IMListener;
import com.mdshi.chatlib.listener.MessageListener;
import com.mdshi.chatlib.listener.SendMessageListener;

import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtbuf.UTF8Buffer;
import org.fusesource.mqtt.client.Callback;
import org.fusesource.mqtt.client.CallbackConnection;
import org.fusesource.mqtt.client.ExtendedListener;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;

import java.net.URISyntaxException;

/**
 * Created by MaDeng on 2018/8/31.
 */
public class IMChat {

    private static IMChat ins;

    private MQTT mqtt;
    private CallbackConnection connection;

    private String iMtopic = "/IM/Chat";
    private Topic[] topics;


    private IMChat(){

    }

    public static void init(String key){
        ins = new IMChat();
        String topics = "/IM/Chat/"+key;
        Topic topic = new Topic(topics, QoS.AT_LEAST_ONCE);
        ins.topics = new Topic[]{topic};
        ins.mqtt("","");
    }

    public static void setMessageListener(final MessageListener listener) {
        ins.connection.listener(new ExtendedListener() {
            @Override
            public void onPublish(UTF8Buffer topic, Buffer body, Callback<Callback<Void>> ack) {
                final byte[] topicb = topic.toByteArray();
                final byte[] data = body.toByteArray();
                final String mTopic = new String(topicb);
                final String mData = new String(data);
                listener.onSuccess();
                if(mTopic.equals(ins.topics[0]))
                    listener.message(mData);
                //TODO 处理消息
            }

            @Override
            public void onConnected() {

            }

            @Override
            public void onDisconnected() {

            }

            @Override
            public void onPublish(UTF8Buffer topic, Buffer body, Runnable ack) {

            }

            @Override
            public void onFailure(Throwable value) {
                listener.onFailure(value);
            }
        });
    }


    public static void setIMListener(final IMListener listener) {
        ins.connection.connect(new Callback<Void>() {
            @Override
            public void onSuccess(Void value) {
                ins.connection.subscribe(ins.topics, new Callback<byte[]>() {
                    @Override
                    public void onSuccess(byte[] value) {
                        listener.onSuccess();
                    }

                    @Override
                    public void onFailure(Throwable value) {
                        listener.onFailure(value);
                    }
                });
            }

            @Override
            public void onFailure(Throwable value) {
                listener.onFailure(value);
            }
        });
    }

    public static void publishMessage(MessageBean bean, final SendMessageListener listener){
        ins.connection.publish(ins.iMtopic, bean.message.getBytes(), QoS.AT_LEAST_ONCE, false, new Callback<Void>() {
            @Override
            public void onSuccess(Void value) {
                if (listener != null) {
                    listener.onSuccess();
                }
            }

            @Override
            public void onFailure(Throwable value) {
                if(listener!=null){
                    listener.onFailure(value);
                }
            }
        });
    }


    public void mqtt(String user, String psw){
        MQTT mqtt = new MQTT();

        try {
            mqtt.setHost(Mqtt_Config.CONNECTION_STRING);
            mqtt.setCleanSession(true);
            mqtt.setReconnectAttemptsMax(Mqtt_Config.RECONNECTION_ATTRMPT_MAX);
            mqtt.setReconnectDelay(Mqtt_Config.RECONNECTION_DELAY);
            mqtt.setKeepAlive(Mqtt_Config.KEEP_ALIVE);
            mqtt.setSendBufferSize(Mqtt_Config.SEND_BUFFER_SIZE);
            mqtt.setUserName(user);
            mqtt.setPassword(psw);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        connection = mqtt.callbackConnection();

    }


}
