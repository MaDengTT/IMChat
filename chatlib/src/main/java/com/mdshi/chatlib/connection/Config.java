package com.mdshi.chatlib.connection;

/**
 * Created by MaDeng on 2018/9/3.
 */
public class Config {
    public String CONNECTION_STRING = "";
    public int CONNECTION_PORT = 1883;

     //重连次数
    public int RECONNECTION_ATTRMPT_MAX = 10;
     //重连间隔
    public int RECONNECTION_DELAY = 2000;
     //心跳时间
    public short KEEP_ALIVE = 30;
     //缓冲大小
    public int SEND_BUFFER_SIZE = 2*1024*1024;

    public String UserName;
    public String Password;

    public String key;
}
