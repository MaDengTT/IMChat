package com.mdshi.component_chat.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mdshi.common.db.entity.MessageEntity;
import com.mdshi.component_chat.R;
import com.mdshi.component_chat.bean.ChatBean;

import java.util.List;

/**
 * Created by MaDeng on 2018/9/6.
 */
public class ChatMessageAdapter extends BaseMultiItemQuickAdapter<ChatBean,BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ChatMessageAdapter(List<ChatBean> data) {
        super(data);
//        addItemType();
        addItemType(ChatBean.Type.TEXT_L.ordinal(), R.layout.chat_left_msg_text_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChatBean item) {

        switch (item.type) {
            case TEXT_R:
            case TEXT_L:

                break;
        }
    }
}
