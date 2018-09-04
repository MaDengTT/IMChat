package com.mdshi.component_chat.ui.chatlist;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.mdshi.common.mvp.BasePresenter;
import com.mdshi.component_chat.bean.ChatBean;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by MaDeng on 2018/9/4.
 */
public class ChatListPresenter extends BasePresenter<ChatListContract.Model,ChatListContract.View> implements ChatListContract.Presenter{

    @Inject
    public ChatListPresenter(ChatListContract.Model model, ChatListContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
