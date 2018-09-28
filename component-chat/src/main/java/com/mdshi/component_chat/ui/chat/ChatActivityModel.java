package com.mdshi.component_chat.ui.chat;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;
import com.mdshi.common.constan.UserData;
import com.mdshi.common.db.entity.MessageEntity;
import com.mdshi.common.db.entity.MessageListEntity;
import com.mdshi.component_chat.ChatManager;
import com.mdshi.component_chat.bean.ChatBean;
import com.mdshi.component_chat.data.ChatRepository;
import com.mdshi.component_chat.utils.BeanUtils;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by MaDeng on 2018/9/11.
 */
public class ChatActivityModel extends ViewModel{

    long user;
    private ChatRepository repository;
    MutableLiveData<List<ChatBean>> addData;


    FlowableProcessor<SessionBean> sessionBus;

    @Inject
    public ChatActivityModel(ChatRepository repository, UserData userdata) {
        userdata.observeForever(userEntity -> user = userEntity.userID);
        this.repository = repository;
        sessionBus = PublishProcessor.create();
        addData = new MutableLiveData<>();
        beanData = new MutableLiveData<>();
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

        beanData.observeForever(sessionBean -> sessionBus.onNext(sessionBean));
    }




    public LiveData<List<ChatBean>> getData() {
        return addData;
    }

    MutableLiveData<SessionBean> beanData;

    public void next(int size) {
        beanData.setValue(beanData.getValue().nextPage(size));
    }

    public void setSessionId(long sessionId) {
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
        public SessionBean nextPage(int size) {
            int temp = size%countPageSize;
            pageSize = temp==0?countPageSize:temp+countPageSize;
            pageNo = size-1;
            return this;
        }
    }


    public void addMsgChatData(ChatBean bean) {
        Flowable.just(bean)
                .flatMap(bean12 -> Flowable.just(BeanUtils.ChatBeanToMsg(bean12)))
                .flatMap(messageEntity -> addMegToDB(messageEntity, bean))
                .map(bean1 -> {
                    ChatManager.getIns().receive(bean1);
                    return bean1;
                }).subscribe();
    }

    private Flowable<ChatBean> addMegToDB(MessageEntity messageEntity, ChatBean bean) {
        return repository.getChatMessageListToSessionId(messageEntity.session_id)
                .flatMap((io.reactivex.functions.Function<MessageListEntity, Publisher<ChatBean>>) entity -> {
                    if (entity.id == 0) {
                        entity = new MessageListEntity();
                        entity.createBean(messageEntity.session_id,
                                user,
                                messageEntity.other_id,
                                messageEntity.type);
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
