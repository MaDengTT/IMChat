package com.mdshi.common.mvp;

import android.support.annotation.NonNull;

/**
 * Created by MaDeng on 2018/5/7.
 */
public interface IView {

    void showLoading();

    void hideLoading();

    void showMessage(@NonNull String message);

}
