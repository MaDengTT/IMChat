package com.mdshi.component_chat.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mdshi.common.db.entity.ContactsEntity;
import com.mdshi.common.image.AvatarConfig;
import com.mdshi.common.image.ImageConfig;
import com.mdshi.common.image.ImageLoader;
import com.mdshi.component_chat.R;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by MaDeng on 2018/9/19.
 */
public class ContactsAdapter extends BaseQuickAdapter<ContactsEntity,BaseViewHolder> {

    private ImageLoader loader;

    @Inject
    public ContactsAdapter(ImageLoader loader) {
        super(R.layout.chat_contacts_item,null);
        this.loader = loader;
    }

    @Override
    protected void convert(BaseViewHolder helper, ContactsEntity item) {
        helper.setText(R.id.tv_name,TextUtils.isEmpty(item.contactsName)?item.info.userName:item.contactsName)
                .setText(R.id.tv_info,String.valueOf(item.contactsId));

        ImageConfig config = new AvatarConfig(helper.getView(R.id.iv_avatar), item.info.avatar);
        loader.loadImaToIv(config);
    }
}
