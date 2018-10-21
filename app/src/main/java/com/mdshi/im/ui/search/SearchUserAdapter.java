package com.mdshi.im.ui.search;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mdshi.common.db.entity.UserEntity;
import com.mdshi.common.image.AvatarConfig;
import com.mdshi.common.image.ImageConfig;
import com.mdshi.common.image.ImageLoader;
import com.mdshi.im.R;


import javax.inject.Inject;

/**
 * Created by MaDeng on 2018/10/9.
 */
public class SearchUserAdapter extends BaseQuickAdapter<UserEntity,BaseViewHolder> {


    ImageLoader loader;
    @Inject
    public SearchUserAdapter(ImageLoader loader) {
        super(R.layout.chat_serach_user_item, null);
        this.loader = loader;
    }


    @Override
    protected void convert(BaseViewHolder helper, UserEntity item) {
        helper.setText(R.id.tv_name,item.userName).setText(R.id.tv_info,String.valueOf(item.userId));

        ImageConfig config = new AvatarConfig(helper.getView(R.id.iv_avatar), item.avatar);
        loader.loadImaToIv(config);
    }
}
