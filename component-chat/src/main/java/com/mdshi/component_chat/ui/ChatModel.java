package com.mdshi.component_chat.ui;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ComputableLiveData;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.persistence.room.InvalidationTracker;
import android.os.SystemClock;
import android.util.ArrayMap;
import android.util.Log;

import com.mdshi.common.db.entity.MessageEntity;
import com.mdshi.common.db.entity.MessageListEntity;
import com.mdshi.component_chat.bean.ChatBean;
import com.mdshi.component_chat.data.ChatRepository;
import com.mdshi.component_chat.di.module.ChatModule;
import com.mdshi.component_chat.listener.ChatListener;

import org.reactivestreams.Publisher;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.Module;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by MaDeng on 2018/9/5.
 */
@Module
public class ChatModel extends ViewModel {
    private LiveData<List<MessageListEntity>> chatList;

    private ChatRepository repository;

    private long userid;

    private ChatListener notificationListener;
    private ChatListener chatListListener;
    private Map<Long, ChatListener> chatListener = new ArrayMap<>();

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

    public void removeNotificationListener() {
        notificationListener = null;
    }

    public void registerNotificationListener(ChatListener notificationListener) {
        this.notificationListener = notificationListener;
    }

    public void removeChatListListener() {
        chatListListener = null;
    }

    public void registerChatListListener(ChatListener chatListListener) {
        this.chatListListener = chatListListener;
    }

    public void removeChatListener(long session_id) {
        chatListener.remove(session_id);
    }

    public void registerChatListener(long session_id,ChatListener chatListener) {
        this.chatListener.put(session_id, chatListener);
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
    public Flowable<List<ChatBean>> getChatMsgData(long id, int pageSize, int pageNo) {
        return repository.getChatMessageList(id, pageSize, pageNo)
                .concatMap(Flowable::fromIterable)
                .flatMap(this::MessageEntity2chatBean)
                .toList()
                .map(temp -> {
                    Log.d(TAG, "getChatMsgData: "+temp.toString());;return temp;})
                .toFlowable();
    }


    FlowableProcessor<Object> bus = PublishProcessor.create().toSerialized();

//    Map<S>

    private Flowable<MessageEntity> chatBean2MessageEntity(ChatBean bean) {
        return Flowable.just(bean)
                .map(chat -> {
                    MessageEntity entity = new MessageEntity();
                    switch (chat.type) {
                        case TEXT_L:
                        case TEXT_R:
                            entity.type = 0;
                            break;
                    }
                    entity.createTime = chat.date;
                    entity.content = chat.content;
                    entity.session_id = chat.session_id;
                    return entity;
                });
    }
    private Flowable<ChatBean> MessageEntity2chatBean(MessageEntity bean) {
        return Flowable.just(bean)
                .map(chat -> {
                    ChatBean entity = new ChatBean();
                    entity.content = chat.content;
                    entity.session_id = chat.session_id;
                    entity.date = chat.createTime;
                    switch (chat.type) {
                        case 0:
                            if (chat.fUserId == userid) {
                                entity.type = ChatBean.Type.TEXT_R;
                            }else {
                                entity.type = ChatBean.Type.TEXT_L;
                            }
                            break;
                    }
                    return entity;
                });
    }

    public Flowable<ChatBean> addMsgChatData(ChatBean bean) {

        //TODO 1,   存入数据库
        //TODO 2,   如果是聊天页，是否ID相同，相同直接推送聊天页，不然则发送通知，
        //          如果是信息表页不发送通知
        //          如果在后台发送通知
        return Flowable.just(bean)
                .flatMap((Function<ChatBean, Publisher<MessageEntity>>) bean12 -> chatBean2MessageEntity(bean12))
                .flatMap((Function<MessageEntity, Publisher<ChatBean>>) messageEntity -> addMegToDB(messageEntity, bean))
                .map(bean1 -> {
                    if (ChatModel.this.chatListListener != null) {
                        ChatModel.this.chatListListener.callback(bean1);
                    }else {
                        ChatListener chatListener = ChatModel.this.chatListener.get(bean1.session_id);
                        if ( chatListener!= null) {
                            chatListener.callback(bean1);
                        }else {
                            notificationListener.callback(bean1);
                        }
                    }
                    return bean1;
                });
    }

    private Flowable<ChatBean> addMegToDB(MessageEntity messageEntity, ChatBean bean) {
        return repository.getChatMessageListToSessionId(messageEntity.session_id)
                .flatMap((Function<MessageListEntity, Publisher<ChatBean>>) entity -> {
                    if (entity == null) {
                        entity = new MessageListEntity();
                        entity.createBean(messageEntity.session_id,
                                userid,
                                messageEntity.other_id,
                                messageEntity.type);

                        repository.addMessageList(entity);
                    }
                    repository.addMessage(messageEntity);
                    entity.updateBean(messageEntity.createTime, messageEntity.id, messageEntity.content);
                    repository.updateMessageList(entity);
                    return Flowable.just(bean);
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


    public Flowable<ChatBean> getChatBeanFlowable(long session_id) {
        Flowable<ChatBean> flowable = bus.ofType(ChatBean.class);


        return flowable;
    }


    public LiveData<List<ChatBean>> getChatListData() {
        return null;
    }

}
