package com.mdshi.component_chat.adapter;



import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mdshi.common.db.bean.MessageListBean;

import com.mdshi.common.image.AvatarConfig;
import com.mdshi.common.image.ImageConfig;
import com.mdshi.common.image.ImageLoader;
import com.mdshi.common.utils.TimeUtils;
import com.mdshi.component_chat.R;


import javax.inject.Inject;

/**
 * Created by MaDeng on 2018/9/3.
 */
public class ChatItemAdapter extends BaseQuickAdapter<MessageListBean,BaseViewHolder> {

    ImageLoader loader;

    @Inject
    public ChatItemAdapter(ImageLoader loader) {
        super(R.layout.chat_message_item,null);
        this.loader = loader;
    }
    @Override
    protected void convert(BaseViewHolder helper, MessageListBean item) {
        helper.setText(R.id.tv_tips, item.unReadNum + "")
                .setText(R.id.tv_chat_time, TimeUtils.date2String(item.createTime))
                .setText(R.id.tv_chat_name, TextUtils.isEmpty(item.contactsName)?item.userName:item.contactsName)
                .setText(R.id.tv_chat_new, item.content)
                .setVisible(R.id.tv_tips, item.unReadNum > 0);

        ImageConfig config = new AvatarConfig(helper.getView(R.id.iv_avatar),item.contactsAvatar);
        loader.loadImaToIv(config);
    }



}
