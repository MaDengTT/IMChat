package com.mdshi.im.ui.show;

import android.arch.lifecycle.ViewModel;

import com.mdshi.common.constan.UserData;

import javax.inject.Inject;

public class ShowViewModel extends ViewModel {

    // TODO: Implement the ViewModel
    UserData userData;

    @Inject
    public ShowViewModel(UserData userData) {
        this.userData = userData;
    }
}
