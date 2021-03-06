package com.mdshi.common.image;

import android.widget.ImageView;

/**
 * Created by MaDeng on 2018/8/31.
 */
public class ImageConfig {
    protected String url;
    protected ImageView imageView;
    protected int placeholder;//占位符
    protected int errorPic;//错误占位符
    protected int roundedCorners = 0;//圆角角度 -1 圆形 0 不变

    public int getRoundedCorners() {
        return roundedCorners;
    }

    public String getUrl() {
        return url;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public int getPlaceholder() {
        return placeholder;
    }

    public int getErrorPic() {
        return errorPic;
    }
}
