package com.mdshi.component_chat;

import android.util.Log;

import com.mdshi.common.db.entity.MessageEntity;
import com.mdshi.common.vo.AppExecutors;
import com.mdshi.component_chat.bean.ChatBean;
import com.mdshi.component_chat.listener.ChatListener;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;

/**
 * Created by MaDeng on 2018/9/11.
 */
public class ChatManager {

    private static ChatManager ins;
    AppExecutors appExecutors;
    private ChatListener listListener;
    private ChatListener notifierListener;
    private ChatListener chatListener;
    private ChatManager() {
        appExecutors = new AppExecutors();
    }

    public void registerListListener(ChatListener listener){
        this.listListener = listener;
    }    
    public void unregisterListListener(ChatListener listener){
        this.listListener = null;
    }
    public void registerChatListener(ChatListener listener){
        this.chatListener = listener;
    }
    public void unregisterChatListener(ChatListener listener){
        this.chatListener = null;
    }
    public void registerNotifierListener(ChatListener listener){
        this.notifierListener = listener;
    }
    public void unregisterNotifierListener(ChatListener listener){
        this.notifierListener = null;
    }


    public static ChatManager getIns() {
        if (ins == null) {
            ins = new ChatManager();
        }
        return ins;
    }

    private void addMessage(ChatBean bean) {
        if (chatListener != null&&!chatListener.callback(bean)) {

        }else if(listListener!=null&&!listListener.callback(bean)){

        } else if (notifierListener != null && !notifierListener.callback(bean)) {

        }
    }

    private static final String TAG = "ChatManager";
    public void receive(ChatBean bean) {
        Log.d(TAG, "receive: "+bean.session_id);
        appExecutors.mainThread().execute(() -> addMessage(bean));
    }


}
