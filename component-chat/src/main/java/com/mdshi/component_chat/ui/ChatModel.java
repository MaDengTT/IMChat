package com.mdshi.component_chat.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.mdshi.common.db.entity.MessageListEntity;

import com.mdshi.component_chat.data.ChatRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.Module;

/**
 * Created by MaDeng on 2018/9/5.
 */
@Module
public class ChatModel extends ViewModel {
    private LiveData<List<MessageListEntity>> chatList;

    private ChatRepository repository;

    private long userid;



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

    public void removeChatValue(MessageListEntity value) {
        repository.removeMessageList(value);
    }

    public void updateChatValue(MessageListEntity value) {
        repository.updateMessageList(value);
    }

    private static final String TAG = "ChatModel";

}
