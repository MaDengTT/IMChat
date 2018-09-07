package com.mdshi.component_chat.ui;

import com.mdshi.common.db.entity.MessageEntity;
import com.mdshi.component_chat.bean.ChatBean;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

import static org.junit.Assert.*;

/**
 * Created by MaDeng on 2018/9/7.
 */
public class ChatModelTest {

    @Test
    public void getChatMsgData() {
        long userid = 0;
        System.out.println("aa");
        getTestData()
        .concatMap(Flowable::fromIterable)
                .map(messageEntity -> {
                    ChatBean bean = new ChatBean();
                    bean.content = messageEntity.content;
                    System.out.println(messageEntity.content);
                    switch (messageEntity.type) {
                        case 0:
                            if(messageEntity.fUserId == userid){bean.type = ChatBean.Type.TEXT_R;
                            } else {bean.type = ChatBean.Type.TEXT_L;}
                            break;
                    }
                    return bean;
                }).toList().toFlowable().subscribe(s->{
            System.out.println(s.get(5));
                    });
    }

    public Flowable<List<MessageEntity>> getTestData() {
        List<MessageEntity> data = new ArrayList<>();

        for(int i = 0 ;i<20;i++) {
            MessageEntity entity = new MessageEntity();
            entity.fUserId = 0;
            entity.type = 0;
            entity.content = "hahah";
            data.add(entity);
        }

        return Flowable.just(data);
    }

}