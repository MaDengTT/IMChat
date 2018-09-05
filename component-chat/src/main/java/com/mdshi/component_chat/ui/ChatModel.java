package com.mdshi.component_chat.ui;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.mdshi.common.base.BaseApplication;
import com.mdshi.common.db.entity.MessageListEntity;
import com.mdshi.component_chat.bean.ChatBean;
import com.mdshi.component_chat.data.ChatRepository;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.Module;

/**
 * Created by MaDeng on 2018/9/5.
 */
@Module
public class ChatModel extends ViewModel {
    private LiveData<List<MessageListEntity>> chatList;

    private ChatRepository repository;

    @Inject
    public ChatModel(ChatRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<MessageListEntity>> getChatList() {
        if (chatList == null) {
            chatList = repository.getChatBean(123456);
        }
        return chatList;
    }


    public void addChatValue(MessageListEntity value) {
//        chatList.getValue().add(value);
       repository.addMessageList(value);
    }


}
