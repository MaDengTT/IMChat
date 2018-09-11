package com.mdshi.component_chat;

import com.mdshi.component_chat.bean.ChatBean;
import com.mdshi.component_chat.listener.ChatListener;

/**
 * Created by MaDeng on 2018/9/11.
 */
public class ChatManager {

    private static ChatManager ins;

    ChatListener listListener;
    ChatListener notifierListener;
    ChatListener chatListener;
    private ChatManager() {

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

    public void receive(ChatBean bean) {
        addMessage(bean);
    }
}
