package com.mdshi.component_chat.utils;

import com.mdshi.common.db.entity.MessageEntity;
import com.mdshi.component_chat.bean.ChatBean;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * Created by MaDeng on 2018/9/28.
 */
public class BeanUtils {

    public static ChatBean MsgToChatBean(MessageEntity entity,long userid) {
        ChatBean c = new ChatBean();
        c.content = entity.content;
        c.session_id = entity.session_id;
        c.date = entity.createTime;
        switch (entity.type) {
            case 0:
                if (entity.fUserId == userid) {
                    c.type = ChatBean.Type.TEXT_R;
                }else {
                    c.type = ChatBean.Type.TEXT_L;
                }
                break;
        }
        return c;
    }
    public static MessageEntity ChatBeanToMsg(ChatBean entity) {
        MessageEntity temp = new MessageEntity();
        switch (entity.type) {
            case TEXT_L:
            case TEXT_R:
                temp.type = 0;
                break;
        }
        temp.createTime = entity.date;
        temp.content = entity.content;
        temp.session_id = entity.session_id;
        temp.tUserId = entity.tUserId;
        temp.fUserId = entity.userId;
        return temp;
    }

}
