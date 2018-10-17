package com.mdshi.im.ui.show;

import android.content.Context;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;

import com.mdshi.common.image.ImageLoader;
import com.mdshi.im.R;


import javax.inject.Inject;

/**
 * Created by MaDeng on 2018/10/17.
 */
public class ShowAdapter extends BaseQuickAdapter<ShowBean,BaseViewHolder> {

    ImageLoader loader;

    @Inject
    public ShowAdapter(ImageLoader loader) {
        super(R.layout.show_image_item,null);
        this.loader = loader;
    }

    @Override
    protected void convert(BaseViewHolder helper, ShowBean item) {
        NineGridImageView niv = helper.getView(R.id.niv);

        niv.setAdapter(new ImageAdapter());
        niv.setImagesData(item.images);
    }

    public class ImageAdapter extends NineGridImageViewAdapter<String> {

        @Override
        protected void onDisplayImage(Context context, ImageView imageView, String s) {
            loader.loadImgToIv(s,imageView);
        }
    }

}
