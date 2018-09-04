package com.mdshi.component_chat.ui.chatlist;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.mdshi.component_chat.bean.ChatBean;

import java.util.List;

/**
 * Created by MaDeng on 2018/9/4.
 */
public class ChatListViewModel extends ViewModel {
    private MutableLiveData<List<ChatBean>> datas;

    public MutableLiveData<List<ChatBean>> getDatas() {
        if (datas == null) {
            datas = new MutableLiveData<>();
        }
        return datas;
    }
}
