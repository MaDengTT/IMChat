package com.mdshi.component_chat.utils;

import com.mdshi.common.db.entity.MessageEntity;
import com.mdshi.component_chat.bean.ChatBean;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * Created by MaDeng on 2018/9/28.
 */
public class BeanUtils {

    public static Flowable<ChatBean> MsgToChatBean(MessageEntity entity,long userid) {
        return Flowable.just(entity).map(entity1 -> {
            ChatBean c = new ChatBean();
            c.content = entity1.content;
            c.session_id = entity1.session_id;
            c.date = entity1.createTime;
            switch (entity1.type) {
                case 0:
                        if (entity.fUserId == userid) {
                            c.type = ChatBean.Type.TEXT_R;
                        }else {
                            c.type = ChatBean.Type.TEXT_L;
                        }
                    break;
            }
            return c;
        });
    }
    public static Flowable<MessageEntity> ChatBeanToMsg(ChatBean entity) {
        return Flowable.just(entity).map(entity1 -> {
            MessageEntity temp = new MessageEntity();
            switch (entity1.type) {
                case TEXT_L:
                case TEXT_R:
                    temp.type = 0;
                    break;
            }
            temp.createTime = entity1.date;
            temp.content = entity1.content;
            temp.session_id = entity1.session_id;
            temp.tUserId = entity1.tUserId;
            temp.fUserId = entity1.userId;
            return temp;
        });
    }

}
