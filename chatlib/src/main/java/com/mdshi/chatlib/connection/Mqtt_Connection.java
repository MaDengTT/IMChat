package com.mdshi.chatlib.connection;

import android.net.sip.SipSession;

import com.mdshi.chatlib.Bean.SendMessage;
import com.mdshi.chatlib.Debug;
import com.mdshi.chatlib.listener.ConnectionListener;
import com.mdshi.chatlib.listener.ReceiveListener;

import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtbuf.UTF8Buffer;
import org.fusesource.mqtt.client.Callback;
import org.fusesource.mqtt.client.CallbackConnection;
import org.fusesource.mqtt.client.ExtendedListener;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;

import java.net.URISyntaxException;
import java.util.concurrent.Callable;

/**
 * Created by MaDeng on 2018/9/3.
 */
public class Mqtt_Connection implements BaseConnection {

    private MQTT mqtt;
    private CallbackConnection connection;
    ConnectionListener connectionListener;
    ReceiveListener receiveListener;

    Config config;
    public Mqtt_Connection(Config config) {
        this.config = config;
        init(config);
    }

    @Override
    public void init(Config config) {
        mqtt = new MQTT();

        try {
            mqtt.setHost(config.CONNECTION_STRING);
            mqtt.setCleanSession(true);
            mqtt.setReconnectAttemptsMax(config.RECONNECTION_ATTRMPT_MAX);
            mqtt.setReconnectDelay(config.RECONNECTION_DELAY);
            mqtt.setKeepAlive(config.KEEP_ALIVE);
            mqtt.setSendBufferSize(config.SEND_BUFFER_SIZE);
            mqtt.setUserName(config.UserName);
            mqtt.setPassword(config.Password);
        } catch (URISyntaxException e) {
            Debug.e("Connection 启动失败！");
            Debug.e(e.getMessage());
        }

    }

    @Override
    public void connectionListener(ConnectionListener listener) {
        this.connectionListener = listener;
    }

    @Override
    public void connect() {
        connection = mqtt.callbackConnection();
        if (connectionListener != null) {
            connectionListener.onStart();
        }
        connection.connect(new Callback<Void>() {
            @Override
            public void onSuccess(Void value) {
                if (connectionListener != null) {
                    connectionListener.onSuccess();
                }
                connection.listener(new ExtendedListener() {
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
                        if (receiveListener != null) {
                            receiveListener.onFailure(value);
                        }
                    }

                    @Override
                    public void onPublish(UTF8Buffer topic, Buffer body, Callback<Callback<Void>> ack) {
                        if (receiveListener != null) {
                            receiveListener.onSuccess();
                            receiveListener.onReciveMessage(topic.toString(),body.toString());
                        }
                    }
                });
            }

            @Override
            public void onFailure(Throwable value) {
                if (connectionListener != null) {
                    connectionListener.onFailure(value);
                }
            }
        });
    }

    @Override
    public void receiveListener(ReceiveListener listener) {
        this.receiveListener = listener;
    }

    @Override
    public void sendMessage(SendMessage message) {
        if (connection != null) {
            connection.publish(message.key, message.body.getBytes(), QoS.AT_LEAST_ONCE, false, new Callback<Void>() {
                @Override
                public void onSuccess(Void value) {

                }

                @Override
                public void onFailure(Throwable value) {

                }
            });
        }
    }


}
