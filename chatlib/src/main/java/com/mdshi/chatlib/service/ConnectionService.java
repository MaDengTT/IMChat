package com.mdshi.chatlib.service;

import com.mdshi.chatlib.connection.BaseConnection;
import com.mdshi.chatlib.listener.ConnectionListener;

/**
 * Created by MaDeng on 2018/11/19.
 */
public class ConnectionService extends IMService {
    public ConnectionService(BaseConnection connection) {
        super(connection);
        getConnection().connectionListener(new ConnectionListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(Throwable value) {

            }
        });
    }


}
