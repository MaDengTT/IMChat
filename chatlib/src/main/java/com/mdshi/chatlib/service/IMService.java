package com.mdshi.chatlib.service;

import com.mdshi.chatlib.connection.BaseConnection;

/**
 * Created by MaDeng on 2018/11/19.
 */
public class IMService {
    BaseConnection connection;

    public IMService(BaseConnection connection) {
        this.connection = connection;
    }

    public BaseConnection getConnection() {
        if (connection == null) {
            throw new NullPointerException("connection isNull");
        }
        return connection;
    }
}
