package com.mdshi.common.image.glide;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.mdshi.common.image.ImageConfig;
import com.mdshi.common.image.ImageLoadListener;
import com.mdshi.common.image.ImageLoader;

/**
 * Created by MaDeng on 2018/8/31.
 */
public class GliderLoader implements ImageLoader {
    @Override
    public void loadImgToIv(String url, ImageView view) {
        GlideApp.with(view)
                .load(url)
                .into(view);
    }

    @Override
    public void loadImaToIv(ImageConfig config) {
        GlideApp.with(config.getImageView())
                .load(config.getUrl())
                .placeholder(config.getPlaceholder())
                .error(config.getErrorPic())
                .into(config.getImageView());
    }

    @Override
    public void loadImage(Context context, String url, ImageLoadListener listener) {
        GlideApp.with(context)
                .load(url)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                    }
                });
    }
}
