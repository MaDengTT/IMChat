package com.mdshi.common.image.glide;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
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
                .apply(getCircle(config.getRoundedCorners()))
                .into(config.getImageView());
    }

    private RequestOptions getCircle(int roundedCorners) {
        if (roundedCorners == -1) {
           return RequestOptions.circleCropTransform()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//不做磁盘缓存
                    .skipMemoryCache(false);//不做内存缓存
        }else {
            //设置图片圆角角度
            RoundedCorners roundedCorner= new RoundedCorners(roundedCorners);
            //通过RequestOptions扩展功能
            RequestOptions options=RequestOptions.bitmapTransform(roundedCorner);
            return options;
        }
    }

    @Override
    public void loadImage(Context context, String url, ImageLoadListener listener) {
        GlideApp.with(context)
                .load(url)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        listener.onResource(resource);
                    }
                });
    }
}
