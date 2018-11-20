package com.mdshi.chatlib;

import android.app.VoiceInteractor;
import android.content.Context;

import com.mdshi.chatlib.connection.BaseConnection;
import com.mdshi.chatlib.connection.Config;
import com.mdshi.chatlib.connection.Mqtt_Connection;
import com.mdshi.chatlib.service.AuthService;
import com.mdshi.chatlib.service.ConnectionService;
import com.mdshi.chatlib.service.IMService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MaDeng on 2018/11/19.
 */
public class IMChatClient {
    private static IMChatClient client;
    private Map<String,IMService> serviceMap = new HashMap<>();

    public IMChatClient(Context context, IMOptions options) {
        Config config = new Config();
        config.CONNECTION_STRING = "www.mdshi.cn";
        config.key = options.appKay;
        BaseConnection connection = new Mqtt_Connection(config);

        serviceMap.put(ConnectionService.class.getCanonicalName(), new ConnectionService(connection));
        serviceMap.put(AuthService.class.getCanonicalName(),new AuthService(connection));
        serviceMap.put(IMService.class.getCanonicalName(),new IMService(connection));
        connection.connect();
    }

    public static void init(Context context,IMOptions options) {
        if (client == null) {
            client = new IMChatClient(context,options);
        }
    }

    public static class IMOptions {
        String appKay;
        Config config;
    }

    public static <T> T getService(Class<T> tClass){
        if (client != null&&client.serviceMap.containsKey(tClass.getCanonicalName())) {
            return (T) client.serviceMap.get(tClass.getCanonicalName());
        }else {
            return null;
        }
    }
}
