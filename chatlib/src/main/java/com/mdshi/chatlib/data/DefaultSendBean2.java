package com.mdshi.chatlib.data;

import com.xuhao.android.libsocket.sdk.bean.ISendable;
import com.zhangwuji.im.ImPacket;
import com.zhangwuji.im.Protocol;
import com.zhangwuji.im.TcpPacket;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by didi on 2018/6/4.
 */

public class DefaultSendBean2 implements ISendable {
    protected TcpPacket content;

    @Override
    public final byte[] parse() {
        ByteBuffer bb = encode(content);
        return bb.array();
    }

    public TcpPacket getContent() {
        return content;
    }

    public void setContent(TcpPacket content) {
        this.content = content;
    }

    public static ByteBuffer encode(TcpPacket tcpPacket){
        int bodyLen = 0;
        byte[] body = tcpPacket.getBody();
        if (body != null)
        {
            bodyLen = body.length;
        }
        boolean isCompress = true;
        boolean is4ByteLength = true;
        boolean isEncrypt = true;
        boolean isHasSynSeq = tcpPacket.getSynSeq() > 0;
        //协议版本号
        byte version = Protocol.VERSION;

        //协议标志位mask
        byte maskByte = ImPacket.encodeEncrypt(version, isEncrypt);
        maskByte = ImPacket.encodeCompress(maskByte, isCompress);
        maskByte = ImPacket.encodeHasSynSeq(maskByte, isHasSynSeq);
        maskByte = ImPacket.encode4ByteLength(maskByte, is4ByteLength);
        byte cmdByte = 0x00;
        if(tcpPacket.getCommand() != null)
            cmdByte = (byte) (cmdByte|tcpPacket.getCommand().getNumber());//消息类型;

        tcpPacket.setVersion(version);
        tcpPacket.setMask(maskByte);

        //bytebuffer的总长度是 = 1byte协议版本号+1byte消息标志位+4byte同步序列号(如果是同步发送则都4byte同步序列号,否则无4byte序列号)+1byte命令码+4byte消息的长度+消息体
        int allLen = 1+1;
        if(isHasSynSeq){
            allLen += 4;
        }
        allLen += 1+4+bodyLen;
        ByteBuffer buffer = ByteBuffer.allocate(allLen);
        //设置字节序
        ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;
        buffer.order(byteOrder);
        buffer.put(tcpPacket.getVersion());
        buffer.put(tcpPacket.getMask());
        if(isHasSynSeq){//同步发送设置4byte，同步序列号;
            buffer.putInt(tcpPacket.getSynSeq());
        }
        buffer.put(cmdByte);
        buffer.putInt(bodyLen);
        buffer.put(body);
        return buffer;
    }
}
