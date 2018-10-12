package com.mdshi.component_chat.adapter;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mdshi.common.constan.UserData;
import com.mdshi.common.db.entity.MessageEntity;
import com.mdshi.common.image.AvatarConfig;
import com.mdshi.common.image.ImageConfig;
import com.mdshi.common.image.ImageLoader;
import com.mdshi.component_chat.R;
import com.mdshi.component_chat.bean.ChatBean;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by MaDeng on 2018/9/6.
 */
public class ChatMessageAdapter extends BaseMultiItemQuickAdapter<ChatBean,BaseViewHolder> {

    ImageLoader loader;

    long myUserId;
    String myUserAvatar;

    String otherAvatar;

    public ChatMessageAdapter setOtherAvatar(String otherAvatar) {
        this.otherAvatar = otherAvatar;
        return this;
    }

    @Inject
    public ChatMessageAdapter(ImageLoader loader, UserData data) {
        this(new ArrayList<>());
        this.loader = loader;
        this.myUserId = data.getValue().userID;
        this.myUserAvatar = data.getValue().avatar;
    }

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
        addItemType(ChatBean.Type.TEXT_R.ordinal(), R.layout.chat_right_msg_text_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChatBean item) {
        Log.d(TAG, "convert: "+item.toString());
        ImageView iVAvatar = helper.getView(R.id.iv_avatar);
        if (iVAvatar == null) {
            Log.d(TAG, "iVavatar is null");
        } else if (item.userId == myUserId) {
            ImageConfig config = new AvatarConfig(iVAvatar, myUserAvatar);
            loader.loadImaToIv(config);
        } else {
            ImageConfig config = new AvatarConfig(iVAvatar, otherAvatar);
            loader.loadImaToIv(config);
        }


        switch (item.type) {
            case TEXT_R:
            case TEXT_L:
                helper.setText(R.id.bv_msg_content, item.content);
                break;
        }
    }
}
