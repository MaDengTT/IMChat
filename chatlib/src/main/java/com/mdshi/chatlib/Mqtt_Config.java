package com.mdshi.chatlib;

/**
 * Created by MaDeng on 2018/8/31.
 */
public interface Mqtt_Config {
    String CONNECTION_STRING = "";
    int CONNECTION_PORT = 1883;

    //重连次数
    int RECONNECTION_ATTRMPT_MAX = 10;
    //重连间隔
    int RECONNECTION_DELAY = 2000;
    //心跳时间
    short KEEP_ALIVE = 30;
    //缓冲大小
    int SEND_BUFFER_SIZE = 2*1024*1024;
}
