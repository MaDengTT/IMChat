package com.mdshi.common.image;

import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.mdshi.common.R;
import com.mdshi.common.utils.RegexUtils;

public class AvatarConfig extends ImageConfig {
        public AvatarConfig(ImageView imageView, String url) {
            this.errorPic = R.drawable.avatar;
            this.placeholder = R.drawable.avatar;
            this.imageView = imageView;
            this.roundedCorners = -1;
            if (TextUtils.isEmpty(url)||RegexUtils.checkURL(url)) {
                this.url = url;
            }else {
                this.url = "http://api.mdshi.cn/"+url;
            }
        }
    }