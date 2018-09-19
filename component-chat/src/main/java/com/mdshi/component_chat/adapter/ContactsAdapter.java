package com.mdshi.component_chat.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mdshi.common.db.entity.ContactsEntity;
import com.mdshi.component_chat.R;

import java.util.List;

/**
 * Created by MaDeng on 2018/9/19.
 */
public class ContactsAdapter extends BaseQuickAdapter<ContactsEntity,BaseViewHolder> {
    public ContactsAdapter(@Nullable List<ContactsEntity> data) {
        super(data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ContactsEntity item) {

    }
}
