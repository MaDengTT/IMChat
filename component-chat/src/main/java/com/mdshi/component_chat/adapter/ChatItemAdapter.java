package com.mdshi.component_chat.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mdshi.common.db.entity.MessageListEntity;
import com.mdshi.common.image.AvatarConfig;
import com.mdshi.common.image.ImageConfig;
import com.mdshi.common.image.ImageLoader;
import com.mdshi.common.utils.TimeUtils;
import com.mdshi.component_chat.R;
import com.mdshi.component_chat.bean.ChatBean;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by MaDeng on 2018/9/3.
 */
public class ChatItemAdapter extends BaseQuickAdapter<MessageListEntity,BaseViewHolder> {

    ImageLoader loader;

    @Inject
    public ChatItemAdapter(ImageLoader loader) {
        super(R.layout.chat_message_item,null);
        this.loader = loader;
    }
    @Override
    protected void convert(BaseViewHolder helper, MessageListEntity item) {
        helper.setText(R.id.tv_tips, item.unReadNum + "")
                .setText(R.id.tv_chat_time, TimeUtils.date2String(item.newDate))
                .setText(R.id.tv_chat_name, item.name)
                .setText(R.id.tv_chat_new, item.newMessageContent)
                .setVisible(R.id.tv_tips, item.unReadNum > 0);

        ImageConfig config = new AvatarConfig(helper.getView(R.id.iv_avatar),item.avatarUrl);
        loader.loadImaToIv(config);
    }



}
