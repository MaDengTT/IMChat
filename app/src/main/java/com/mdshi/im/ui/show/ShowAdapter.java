package com.mdshi.im.ui.show;

import android.content.Context;

import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;

import com.mdshi.common.db.entity.CircleEntity;
import com.mdshi.common.image.AvatarConfig;
import com.mdshi.common.image.ImageLoader;
import com.mdshi.common.utils.RegexUtils;
import com.mdshi.im.R;


import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by MaDeng on 2018/10/17.
 */
public class ShowAdapter extends BaseQuickAdapter<CircleEntity,BaseViewHolder> {

    ImageLoader loader;

    @Inject
    public ShowAdapter(ImageLoader loader) {
        super(R.layout.show_image_item,null);
        this.loader = loader;
    }

    @Override
    protected void convert(BaseViewHolder helper, CircleEntity item) {
        NineGridImageView niv = helper.getView(R.id.niv);

        niv.setAdapter(new ImageAdapter());
        List<String> images =  Arrays.asList(item.images.split(","));
        niv.setImagesData(images);

        helper.setText(R.id.tv_name, item.userInfo.userName);
        helper.setText(R.id.tv_content, item.content);
        ImageView avatar = helper.getView(R.id.iv_avatar);
        loader.loadImaToIv(new AvatarConfig(avatar,item.userInfo.avatar));
    }

    public class ImageAdapter extends NineGridImageViewAdapter<String> {

        @Override
        protected void onDisplayImage(Context context, ImageView imageView, String s) {
            if (TextUtils.isEmpty(s)||RegexUtils.checkURL(s)) {
//                this.s = url;
            }else {
                s = "http://api.mdshi.cn/"+s;
            }
            loader.loadImgToIv(s,imageView);
        }
    }

}
