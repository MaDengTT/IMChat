package com.mdshi.component_chat.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.mdshi.common.db.entity.MessageEntity;
import com.mdshi.common.db.entity.MessageListEntity;
import com.mdshi.component_chat.bean.ChatBean;
import com.mdshi.component_chat.data.ChatRepository;

import org.reactivestreams.Publisher;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.Module;
import io.reactivex.Flowable;
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


    public Flowable<List<ChatBean>> getChatMsgData(long id, int pageSize, int pageNo) {
        return repository.getChatMessageList(id, pageSize, pageNo)
                .concatMap(Flowable::fromIterable)
                .map(messageEntity -> {
                    ChatBean bean = new ChatBean();
                    bean.content = messageEntity.content;
                    switch (messageEntity.type) {
                        case 0:
                            if (messageEntity.fUserId == userid) {
                                bean.type = ChatBean.Type.TEXT_R;
                            } else {
                                bean.type = ChatBean.Type.TEXT_L;
                            }
                            break;
                    }
                    return bean;
                }).toList().toFlowable();
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
                    entity.content = chat.content;
                    entity.session_id = chat.session_id;
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
                .map(messageEntity -> {
                    repository.addMessage(messageEntity);
                    return bean;
                });
    }


    public Flowable<ChatBean> getChatBeanFlowable(long session_id) {
        Flowable<ChatBean> flowable = bus.ofType(ChatBean.class);


        return flowable;
    }

}
