package com.mdshi.chatlib.service;

import com.mdshi.chatlib.connection.BaseConnection;
import com.mdshi.chatlib.listener.AdoptableFuture;
import com.mdshi.chatlib.listener.ConnectionListener;
import com.mdshi.chatlib.listener.Promise;

/**
 * Created by MaDeng on 2018/11/19.
 */
public class ConnectionService extends IMService {



    public ConnectionService(BaseConnection connection) {
        super(connection);
    }

    private boolean isConnect() {
        return false;
    }

    public AdoptableFuture<Void> connect() {
        final Promise<Void> promise = new Promise<>();
        getConnection().connectionListener(new ConnectionListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess() {
                promise.onSuccess(null);
            }

            @Override
            public void onFailure(Throwable value) {
              promise.onException(value);
            }
        });
        getConnection().connect();
      return promise;
    }

}
