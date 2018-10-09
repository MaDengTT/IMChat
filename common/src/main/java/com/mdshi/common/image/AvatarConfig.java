package com.mdshi.common.image;

import android.widget.ImageView;

import com.mdshi.common.R;

public class AvatarConfig extends ImageConfig {
        public AvatarConfig(ImageView imageView, String url) {
            this.errorPic = R.drawable.avatar;
            this.placeholder = R.drawable.avatar;
            this.imageView = imageView;
            this.url = url;
        }
    }