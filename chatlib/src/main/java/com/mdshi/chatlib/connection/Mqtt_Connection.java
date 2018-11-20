package com.mdshi.chatlib.connection;

import android.util.Log;

import com.mdshi.chatlib.Bean.SendMessage;
import com.mdshi.chatlib.Debug;
import com.mdshi.chatlib.listener.BaseListener;
import com.mdshi.chatlib.listener.ConnectionListener;
import com.mdshi.chatlib.listener.ReceiveListener;
import com.mdshi.chatlib.listener.RequestCallback;

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
 * Created by MaDeng on 2018/9/3.
 */
public class Mqtt_Connection implements BaseConnection {

    private MQTT mqtt;
    private CallbackConnection connection;
    ConnectionListener connectionListener;
    ReceiveListener receiveListener;

    Config config;
    private Topic[] topics = new Topic[1];

    public Mqtt_Connection(Config config) {
        this.config = config;
        init(config);
    }

    @Override
    public void init(Config config) {
        mqtt = new MQTT();
        try {
            mqtt.setHost("tcp://"+config.CONNECTION_STRING+":"+config.CONNECTION_PORT);
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

    public void sub(final String sub, final RequestCallback<String> callback) {
        if (topics[0] != null) {
            unSub(new RequestCallback<String>() {
                @Override
                public void onSuccess(String param) {

                }

                @Override
                public void onFailed(int code) {

                }

                @Override
                public void onException(Throwable exception) {
                }
            });
        }
        Topic topic = new Topic(sub, QoS.AT_LEAST_ONCE);
        topics[0] = topic;
        connection.subscribe(topics, new Callback<byte[]>() {
            @Override
            public void onSuccess(byte[] value) {
                Debug.d("Sub success:"+sub+":value:"+new String(value));
                callback.onSuccess(new String(value));
            }

            @Override
            public void onFailure(Throwable value) {
                Debug.e("Sub Failure:"+sub+":"+value.toString());
                Log.e("IMChat", "onFailure: ",value );
                callback.onException(value);
            }
        });
    }

    public void unSub(final RequestCallback<String> callback) {
        if (topics[0] != null) {
            final UTF8Buffer[] utf8Buffers = new UTF8Buffer[1];
            utf8Buffers[0] = topics[0].name();
            connection.unsubscribe(utf8Buffers, new Callback<Void>() {
                @Override
                public void onSuccess(Void value) {
                    Debug.d("unSub success:"+utf8Buffers[0]);
                    callback.onSuccess(utf8Buffers.toString());
                }

                @Override
                public void onFailure(Throwable value) {
                    Debug.e("unSub Failure:"+utf8Buffers[0]+":"+value.toString());
                    callback.onException(value);
                }
            });
        }
        topics[0] = null;
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
                            receiveListener.onReceiveMessage(topic.toString(),body.utf8().toString());
                            receiveListener.onSuccess();
                        }
                    }
                });
                if (connectionListener != null) {
                    connectionListener.onSuccess();
                }
            }

            @Override
            public void onFailure(Throwable value) {
                Log.e("IMChat", "onFailure: ",value );
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
    public void sendMessage(final SendMessage message) {
        if (connection != null) {
            connection.publish(message.key, message.body.getBytes(), QoS.EXACTLY_ONCE, false, new Callback<Void>() {
                @Override
                public void onSuccess(Void value) {
                    Debug.d("publish : "+message.key);
                }

                @Override
                public void onFailure(Throwable value) {
                    Debug.e("publish : ",value);
                }
            });
        }
    }
    @Override
    public void sendMessage(final SendMessage message, final RequestCallback listener) {
        if (connection != null) {
            connection.publish(message.key, message.body.getBytes(), QoS.EXACTLY_ONCE, false, new Callback<Void>() {
                @Override
                public void onSuccess(Void value) {
                    Debug.d("publish : "+message.key);
                   listener.onSuccess(Void.TYPE);
                }

                @Override
                public void onFailure(Throwable value) {
                    Debug.e("publish : ",value);
                    listener.onException(value);
                }
            });
        }
    }


}
