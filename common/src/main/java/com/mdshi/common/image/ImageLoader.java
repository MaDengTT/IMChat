package com.mdshi.common.image;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by MaDeng on 2018/8/31.
 */
public interface ImageLoader {
    void loadImgToIv(String url, ImageView view);
    void loadImaToIv(ImageConfig config);
    void loadImage(Context context, String url, ImageLoadListener listener);
}
