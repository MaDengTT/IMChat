package com.xuhao.android.libsocket.impl.blockio.io;

import com.xuhao.android.libsocket.impl.exceptions.ReadException;
import com.xuhao.android.libsocket.sdk.OkSocketOptions;
import com.xuhao.android.libsocket.sdk.bean.OriginalData;
import com.xuhao.android.libsocket.sdk.connection.abilities.IStateSender;
import com.xuhao.android.libsocket.sdk.connection.interfacies.IAction;
import com.xuhao.android.libsocket.sdk.protocol.IHeaderProtocol;
import com.xuhao.android.libsocket.utils.BytesUtils;
import com.xuhao.android.libsocket.utils.SL;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * Created by xuhao on 2017/5/31.
 */

public class ReaderImpl extends AbsReader {

    private ByteBuffer mRemainingBuf;

    private ByteBuffer mCacheBuf=null; //针对半包处理情况。缓存完整包的数据
    private ByteBuffer mCacheheadBuf=null; //针对半包，缓存包的完整头部信息
    private  int mCachebodylen=0; //针对半包，缓存包body长度

    public ReaderImpl(InputStream inputStream, IStateSender stateSender) {
        super(inputStream, stateSender);
    }

    @Override
    public void read() throws RuntimeException {
        OriginalData originalData = new OriginalData();
        IHeaderProtocol headerProtocol = mOkOptions.getHeaderProtocol();
        ByteBuffer headBuf = ByteBuffer.allocate(headerProtocol.getHeaderLength());
        headBuf.order(mOkOptions.getReadByteOrder());
        try {
                //todo: 开始解析包头
                if (mRemainingBuf != null) {
                    mRemainingBuf.flip();
                    int length = Math.min(mRemainingBuf.remaining(), headerProtocol.getHeaderLength());
                    headBuf.put(mRemainingBuf.array(), 0, length);
                    if (length < headerProtocol.getHeaderLength()) { //如果缓存的包长度小于协议头长度。丢掉此包。
                        //there are no data left
                        mRemainingBuf = null;
                        readHeaderFromChannel(headBuf, headerProtocol.getHeaderLength() - length);
                    } else {
                        mRemainingBuf.position(headerProtocol.getHeaderLength());
                    }
                } else {
                    readHeaderFromChannel(headBuf, headBuf.capacity());
                }

                originalData.setHeadBytes(headBuf.array());

                if (OkSocketOptions.isDebug()) {
                    SL.i("read head: " + BytesUtils.toHexStringForLog(headBuf.array()));
                }

                //todo:开始解析包内容
                int bodyLength = headerProtocol.getBodyLength(originalData.getHeadBytes(), mOkOptions.getReadByteOrder());


                if (OkSocketOptions.isDebug()) {
                    SL.i("need read body length: " + bodyLength);
                }
                if (bodyLength > 0) {
                    if (bodyLength > mOkOptions.getMaxReadDataMB() * 1024 * 1024) {
                        throw new ReadException("Need to follow the transmission protocol.\r\n" +
                                "Please check the client/server code.\r\n" +
                                "According to the packet header data in the transport protocol, the package length is " + bodyLength + " Bytes.");
                    }
                    ByteBuffer byteBuffer = ByteBuffer.allocate(bodyLength);
                    byteBuffer.order(mOkOptions.getReadByteOrder());
                    if (mRemainingBuf != null) {
                        int bodyStartPosition = mRemainingBuf.position();
                        int length = Math.min(mRemainingBuf.remaining(), bodyLength);
                        byteBuffer.put(mRemainingBuf.array(), bodyStartPosition, length);
                        mRemainingBuf.position(bodyStartPosition + length);
                        if (length == bodyLength) {
                            if (mRemainingBuf.remaining() > 0) {//there are data left
                                ByteBuffer temp = ByteBuffer.allocate(mRemainingBuf.remaining());
                                temp.order(mOkOptions.getReadByteOrder());
                                temp.put(mRemainingBuf.array(), mRemainingBuf.position(), mRemainingBuf.remaining());
                                mRemainingBuf = temp;
                            } else {//there are no data left
                                mRemainingBuf = null;
                            }
                            //cause this time data from remaining buffer not from channel.
                            originalData.setBodyBytes(byteBuffer.array());
                            mStateSender.sendBroadcast(IAction.ACTION_READ_COMPLETE, originalData);
                            return;
                        } else {//there are no data left in buffer and some data pieces in channel
                            mRemainingBuf = null;
                        }
                    }
                    readBodyFromChannel(byteBuffer);
                    originalData.setBodyBytes(byteBuffer.array());
                } else if (bodyLength == 0) {
                    originalData.setBodyBytes(new byte[0]);
                    if (mRemainingBuf != null) {
                        //the body is empty so header remaining buf need set null
                        if (mRemainingBuf.hasRemaining()) {
                            ByteBuffer temp = ByteBuffer.allocate(mRemainingBuf.remaining());
                            temp.order(mOkOptions.getReadByteOrder());
                            temp.put(mRemainingBuf.array(), mRemainingBuf.position(), mRemainingBuf.remaining());
                            mRemainingBuf = temp;
                        } else {
                            mRemainingBuf = null;
                        }
                    }
                } else if (bodyLength < 0) {
                    throw new ReadException(
                            "this socket input stream is end of file read " + bodyLength + " ,we'll disconnect");
                }

                mStateSender.sendBroadcast(IAction.ACTION_READ_COMPLETE, originalData);

        } catch (Exception e) {
            ReadException readException = new ReadException(e);
            throw readException;
        }
    }

    private void readHeaderFromChannel(ByteBuffer headBuf, int readLength) throws IOException {
        for (int i = 0; i < readLength; i++) {
            byte value = (byte) mInputStream.read();
            if (value == -1) {
                throw new ReadException(
                        "this socket input stream is end of file read " + value + " ,we'll disconnect");
            }
            headBuf.put(value);
        }
    }

    private void readBodyFromChannel(ByteBuffer byteBuffer) throws IOException {

            while (byteBuffer.hasRemaining()) { //是否还有空位
                try {
                    byte[] bufArray = new byte[mOkOptions.getReadPackageBytes()];//一次性读取数据的长度
                    int len = mInputStream.read(bufArray);  //获取数据。并返回读取的数据长度
                    if (len == -1) {
                        break;
                    }

                    String str = new String(bufArray, Charset.forName("utf-8"));
                    SL.i("dis: " + str);

                  //  byteBuffer = ByteBuffer.allocate(len);
                    int remaining = byteBuffer.remaining();  //获取剩余的空位
                    if (len > remaining) { //如果读取的数据，大于剩余空位  这里应该是解决粘包的问题
                        byteBuffer.put(bufArray, 0, remaining);
                        mRemainingBuf = ByteBuffer.allocate(len - remaining);
                        mRemainingBuf.order(mOkOptions.getReadByteOrder());
                        mRemainingBuf.put(bufArray, remaining, len - remaining);

                        String str2 = new String(mRemainingBuf.array(), Charset.forName("utf-8"));
                        SL.i("dis: " + str);
                    }
                   else { //这里面。可能要做半包处理
                        byteBuffer.put(bufArray, 0, len);//读取的数据小于或等于长度  全部put到body里面。
                    }
                } catch (Exception e) {
                    throw e;
                }
            }

        if (OkSocketOptions.isDebug()) {
            SL.i("read total bytes: " + BytesUtils.toHexStringForLog(byteBuffer.array()));
            SL.i("read total length:" + (byteBuffer.capacity() - byteBuffer.remaining()));
        }
    }

}
