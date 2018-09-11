package com.mdshi.component_chat.ui.chat;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;


import com.mdshi.common.db.entity.MessageEntity;
import com.mdshi.common.db.entity.MessageListEntity;
import com.mdshi.component_chat.bean.ChatBean;
import com.mdshi.component_chat.data.ChatRepository;


import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by MaDeng on 2018/9/11.
 */
public class ChatActivityModel extends ViewModel{

    private ChatRepository repository;
    long userid = 123456;
    LiveData<List<ChatBean>> data;
    MutableLiveData<ChatBean> newData;
    @Inject
    public ChatActivityModel(ChatRepository repository ){
        this.repository = repository;
    }

    private static final String TAG = "ChatActivityModel";
    public LiveData<List<ChatBean>> getData(long id, int pageSize, int pageNo) {
        if (data == null) {
            data = new MutableLiveData<>();
        }

        LiveData<List<MessageEntity>> chatListData = repository.getChatListData(id, pageSize, pageNo);
        data = Transformations.map(chatListData, input -> {
            if (input == null) {
                return new ArrayList<>();
            }
            List<ChatBean> data = new ArrayList<>();
            for(int i=0;i<input.size();i++) {
                data.add(MessageEntity2chatBean(input.get(i)));
            }
            return data;
        });
        return data;
    }

    public LiveData<ChatBean> getData(long id) {
        if (newData == null) {
            newData = new MutableLiveData<>();
        }
        return newData;
    }

    private MessageEntity chatBean2MessageEntity(ChatBean bean) {
        MessageEntity entity = new MessageEntity();
        switch (bean.type) {
            case TEXT_L:
            case TEXT_R:
                entity.type = 0;
                break;
        }
        entity.createTime = bean.date;
        entity.content = bean.content;
        entity.session_id = bean.session_id;
        entity.fUserId = userid;
        return entity;
    }
    private ChatBean MessageEntity2chatBean(MessageEntity bean) {
        ChatBean entity = new ChatBean();
        entity.content = bean.content;
        entity.session_id = bean.session_id;
        entity.date = bean.createTime;
        switch (bean.type) {
            case 0:
                if (bean.fUserId == userid) {
                    entity.type = ChatBean.Type.TEXT_R;
                }else {
                    entity.type = ChatBean.Type.TEXT_L;
                }
                break;
        }
        return entity;
    }

    public void addMsgChatData(ChatBean bean) {
        //TODO 1,   存入数据库
        //TODO 2,   如果是聊天页，是否ID相同，相同直接推送聊天页，不然则发送通知，
        //          如果是信息表页不发送通知
        //          如果在后台发送通知
        Flowable.just(bean)
                .map(bean12 -> chatBean2MessageEntity(bean12))
                .flatMap((io.reactivex.functions.Function<MessageEntity, Publisher<ChatBean>>) messageEntity -> addMegToDB(messageEntity, bean))
                .map(bean1 -> {
//                    if (ChatModel.this.chatListListener != null) {
//                        ChatModel.this.chatListListener.callback(bean1);
//                    }else {
//                        ChatListener chatListener = ChatModel.this.chatListener.get(bean1.session_id);
//                        if ( chatListener!= null) {
//                            chatListener.callback(bean1);
//                        }else {
//                            notificationListener.callback(bean1);
//                        }
//                    }
                    newData.setValue(bean1);
                    return bean1;
                }).subscribe();
    }

    private Flowable<ChatBean> addMegToDB(MessageEntity messageEntity, ChatBean bean) {
        return repository.getChatMessageListToSessionId(messageEntity.session_id)
                .flatMap((io.reactivex.functions.Function<MessageListEntity, Publisher<ChatBean>>) entity -> {
                    if (entity.id == 0) {
                        entity = new MessageListEntity();
                        entity.createBean(messageEntity.session_id,
                                userid,
                                messageEntity.other_id,
                                messageEntity.type);

//                        repository.addMessageList(entity);
                        repository.addNewListMessage(entity,messageEntity);
                    }else {
                        repository.addMessage(messageEntity);
                    }
                    entity.updateBean(messageEntity.createTime, messageEntity.id, messageEntity.content);
                    repository.updateMessageList(entity);
                    return Flowable.just(bean);
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
