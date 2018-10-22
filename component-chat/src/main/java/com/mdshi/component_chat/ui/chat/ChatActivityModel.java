package com.mdshi.component_chat.ui.chat;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.google.gson.Gson;
import com.mdshi.chatlib.Bean.MessageBean;
import com.mdshi.chatlib.IMChat;
import com.mdshi.chatlib.listener.SendMessageListener;
import com.mdshi.common.constan.UserData;
import com.mdshi.common.db.bean.UserInfo;
import com.mdshi.common.db.entity.ContactsEntity;
import com.mdshi.component_chat.ChatManager;
import com.mdshi.component_chat.bean.ChatBean;
import com.mdshi.component_chat.data.ChatRepository;
import com.mdshi.component_chat.utils.BeanUtils;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by MaDeng on 2018/9/11.
 */
public class ChatActivityModel extends ViewModel{

    private long user;
    private ChatRepository repository;
    private MutableLiveData<List<ChatBean>> addData;

    private Gson gson;

    private FlowableProcessor<SessionBean> sessionBus;

    private UserInfo userInfo;

    @Inject
    public ChatActivityModel(ChatRepository repository, UserData userdata) {
        userdata.observeForever(userEntity -> {user = userEntity != null ? userEntity.userId : 0;
            userInfo = new UserInfo();
            userInfo.userId = userEntity.userId;
            userInfo.avatar = userEntity.avatar;
            userInfo.email = userEntity.email;
            userInfo.userName = userEntity.userName;
            userInfo.phone = userEntity.phone;
        });
        this.repository = repository;
        sessionBus = PublishProcessor.create();
        addData = new MutableLiveData<>();
        beanData = new MutableLiveData<>();
        gson = new Gson();
        sessionBus.flatMap(aLong ->
                repository.getChatMessageList(aLong.sessionId, aLong.pageSize, aLong.pageNo))
        .flatMap(messageEntities -> {
            List<ChatBean> data = new ArrayList<>();
            for(int i=0;i<messageEntities.size();i++) {
                data.add(BeanUtils.MsgToChatBean(messageEntities.get(i), user));
            }
            addData.setValue(data);
            Log.d(TAG, "onNext: "+data.size());
            return Flowable.just(data);
        }).subscribe(new Subscriber<List<ChatBean>>() {
            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(List<ChatBean> chatBeans) {
            }

            @Override
            public void onError(Throwable t) {
                Log.e(TAG, "onError: ",t );
            }

            @Override
            public void onComplete() {

            }
        });

        beanData.observeForever(sessionBean -> {sessionBus.onNext(sessionBean);});
    }


    public LiveData<ContactsEntity> getContact(long contactsId){
        return repository.getContact(user,contactsId);
    }

    public LiveData<List<ChatBean>> getData() {
        return addData;
    }

    private MutableLiveData<SessionBean> beanData;

    void next(int size) {
        beanData.setValue(beanData.getValue().nextPage(size));
    }

    void setSessionId(long sessionId) {
        SessionBean bean = new SessionBean();
        bean.sessionId = sessionId;
        bean.pageSize = 30;
        bean.pageNo = 0;
        beanData.setValue(bean);
    }

    private static final String TAG = "ChatActivityModel";

    public class SessionBean{
        long sessionId;
        int pageSize;
        int pageNo;

        int countPageSize = 30;
        SessionBean nextPage(int size) {
            int temp = countPageSize - size%countPageSize;
            pageSize = temp==0?countPageSize:temp+countPageSize;
            pageNo = size;
            return this;
        }
    }


    void addMsgChatData(ChatBean bean) {
        Flowable.just(bean)
                .map(bean1 -> {
                    ChatManager.getIns().receive(bean1);
                    return bean1;
                })
                .flatMap(bean12 -> Flowable.just(BeanUtils.ChatBeanToMsg(bean12,userInfo)))
                .map(bean13->{repository.chatMessageToDb(bean13,user,bean13.tUserId);return bean13;})
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(s1-> Flowable.create((FlowableOnSubscribe<ChatBean>) emitter -> {
                    MessageBean messageBean = new MessageBean(MessageBean.CHAT_KEY,gson.toJson(s1));
                    IMChat.sendMessage(messageBean, new SendMessageListener() {
                        @Override
                        public void onSuccess() {
                            emitter.onNext(bean);
                            emitter.onComplete();
                        }

                        @Override
                        public void onFailure(Throwable value) {
                            emitter.onError(value);
                        }
                    });
                },BackpressureStrategy.BUFFER))
                .doOnError(err-> Log.e(TAG, "addMsgChatData: ", err))
                .subscribe();
    }
}
