package com.mdshi.component_chat.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mdshi.component_chat.R;
import com.mdshi.component_chat.bean.ChatBean;

import java.util.List;

/**
 * Created by MaDeng on 2018/9/3.
 */
public class ChatItemAdapter extends BaseQuickAdapter<ChatBean,BaseViewHolder> {
    public ChatItemAdapter(@Nullable List<ChatBean> data) {
        super(R.layout.chat_message_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChatBean item) {

    }
}
