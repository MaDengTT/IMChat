package com.mdshi.chatlib.connection;

import android.content.Context;
import android.icu.text.StringPrepParseException;
import android.widget.Toast;

import com.mdshi.chatlib.Bean.MessageBean;
import com.mdshi.chatlib.Bean.SendMessage;
import com.mdshi.chatlib.Debug;
import com.mdshi.chatlib.data.HandShake2;
import com.mdshi.chatlib.listener.ConnectionListener;
import com.mdshi.chatlib.listener.ReceiveListener;
import com.xuhao.android.libsocket.sdk.ConnectionInfo;
import com.xuhao.android.libsocket.sdk.OkSocket;
import com.xuhao.android.libsocket.sdk.OkSocketOptions;
import com.xuhao.android.libsocket.sdk.SocketActionAdapter;
import com.xuhao.android.libsocket.sdk.bean.IPulseSendable;
import com.xuhao.android.libsocket.sdk.bean.ISendable;
import com.xuhao.android.libsocket.sdk.bean.OriginalData;
import com.xuhao.android.libsocket.sdk.connection.IConnectionManager;
import com.xuhao.android.libsocket.sdk.connection.NoneReconnect;
import com.xuhao.android.libsocket.sdk.protocol.IHeaderProtocol;
import com.zhangwuji.im.ImPacket;
import com.zhangwuji.im.TcpPacket;
import com.zhangwuji.im.packets.ChatBody;
import com.zhangwuji.im.packets.Command;
import com.zhangwuji.im.packets.LoginReqBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
/**
 * Created by MaDeng on 2018/10/31.
 */
public class J_IM_Connection implements BaseConnection {

    Config config;
    IConnectionManager manager;
    OkSocketOptions options;
    private ConnectionInfo mInfo;
    private ConnectionListener connectionListener;
    private ReceiveListener receiveListener;
    private OkSocketOptions.Builder okOptionsBuilder;

    public J_IM_Connection(Config config) {
        this.config = config;
        init(config);
    }

    private SocketActionAdapter adapter = new SocketActionAdapter() {
        @Override
        public void onSocketConnectionSuccess(Context context, ConnectionInfo info, String action) {
            connectionListener.onSuccess();
        }

        @Override
        public void onSocketDisconnection(Context context, ConnectionInfo info, String action, Exception e) {
            if (e != null) {
//                logSend("异常断开:" + e.getMessage());
                connectionListener.onFailure(e);
            } else {
//                logSend("正常断开");
            }
        }

        @Override
        public void onSocketConnectionFailed(Context context, ConnectionInfo info, String action, Exception e) {
//            Toast.makeText(context, "连接失败" + e.getMessage(), LENGTH_SHORT).show();
//            logSend("连接失败");
            connectionListener.onFailure(e);
        }
        @Override
        public void onSocketReadResponse(Context context, ConnectionInfo info, String action, OriginalData data) {
            super.onSocketReadResponse(context, info, action, data);

            byte[] head=data.getHeadBytes();
            ByteBuffer buffer = ByteBuffer.wrap(head);
            byte n2=  buffer.get(2);

            Command cmd=Command.valueOf(n2);//命令码

            String str = new String(data.getBodyBytes(), Charset.forName("utf-8"));
            Debug.d("onSocketReadResponse: Command:"+cmd+" msg:"+str);
            switch (cmd) {
                case COMMAND_LOGIN_RESP:
                    try {
                        JSONObject object = new JSONObject(str);
                        int code = (int) object.get("code");
                        if (code == 10008) {
                            connectionListener.onFailure(new Exception("登录失败"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case COMMAND_CHAT_REQ:
                    try {
                        JSONObject object = new JSONObject(str);
                        JSONObject jsonData = object.getJSONObject("data");
                        String sdata = jsonData.getString("content");
                        receiveListener.onReciveMessage(MessageBean.R_CHAT_KEY,sdata);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case COMMAND_CHAT_RESP:
//                    Command:COMMAND_CHAT_RESP msg:{"code":10000,"command":12,"msg":"ok 发送成功"}
//                    Command:COMMAND_CHAT_RESP msg:{"code":10001,"command":12,"msg":"offline 用户不在线"}
                    break;
            }
//            logRece(cmd.toString()+str);
        }

        @Override
        public void onSocketWriteResponse(Context context, ConnectionInfo info, String action, ISendable data) {
            super.onSocketWriteResponse(context, info, action, data);
            String str = new String(data.parse(), Charset.forName("utf-8"));
//            logSend(str);
        }

        @Override
        public void onPulseSend(Context context, ConnectionInfo info, IPulseSendable data) {
            super.onPulseSend(context, info, data);
            String str = new String(data.parse(), Charset.forName("utf-8"));
//            logSend(str);
        }
    };

    @Override
    public void init(Config config) {
        mInfo = new ConnectionInfo(config.CONNECTION_STRING, config.CONNECTION_PORT);

        options = new OkSocketOptions.Builder()
                .setReconnectionManager(new NoneReconnect())
                .setWritePackageBytes(1024)
                .setReadPackageBytes(1024)
                .build();

        okOptionsBuilder = new OkSocketOptions.Builder(options);
        okOptionsBuilder.setHeaderProtocol(new IHeaderProtocol() {
            @Override
            public int getHeaderLength() {
                //返回自定义的包头长度,框架会解析该长度的包头
                return 7;
            }

            @Override
            public int getBodyLength(byte[] header, ByteOrder byteOrder) {
                //从header(包头数据)中解析出包体的长度,byteOrder是你在参配中配置的字节序,可以使用ByteBuffer比较方便解析
                ByteBuffer buffer = ByteBuffer.wrap(header);
                int bodylen=0;

                //协议头索引;
                int index = 0;

                //获取第一个字节协议版本号;
                byte n1= buffer.get(index);
                if(n1!=0x01)
                {
                    return -1;
                }
                index++;
                //标志位
                byte maskByte = buffer.get(index);
                if(ImPacket.decodeHasSynSeq(maskByte)){//同步发送;
                    index += 4;
                }
                index++;
                //cmd命令码
                byte n2=  buffer.get(index);
                index++;
                //消息体长度
                bodylen= buffer.getInt(index);

                //  bodylen=buffer.get(7);
                return bodylen;
            }
        });


    }

    @Override
    public void connectionListener(ConnectionListener listener) {
        this.connectionListener = listener;
    }

    @Override
    public void connect() {
        connectionListener.onStart();
        manager = OkSocket.open(mInfo).option(okOptionsBuilder.build());
        manager.registerReceiver(adapter);
        manager.connect();
    }

    @Override
    public void receiveListener(ReceiveListener listener) {
        this.receiveListener = listener;
    }

    @Override
    public void sendMessage(SendMessage message) {
        if (manager == null) {
//            return;
//            manager = OkSocket.open(mInfo).option(okOptionsBuilder.build());
            connect();
        }
        if (!manager.isConnect()) {
            //未连接
            manager.connect();
        }else {
            ChatBody chatBody = new ChatBody()
                    .setFrom(message.from)
//                    .setTo(message.to)
                    .setTo(message.to)
                    .setMsgType(message.messageType)
                    .setChatType(message.chatType)
                    .setContent(message.body);
            TcpPacket tcpPacket = new TcpPacket(Command.COMMAND_CHAT_REQ, chatBody.toByte());
            HandShake2 handShake2 = new HandShake2(tcpPacket);
            manager.send(handShake2);

        }
    }

    @Override
    public void sub(String sub) {
        byte[] loginBody = new LoginReqBody(sub).toByte();
//        byte[] loginBody = new LoginReqBody("18538008584","123456").toByte();
        TcpPacket loginPacket = new TcpPacket(Command.COMMAND_LOGIN_REQ,loginBody);
        manager.send(new HandShake2(loginPacket));
    }

    @Override
    public void unSub() {
        manager.disconnect();
        manager.unRegisterReceiver(adapter);
    }
}
