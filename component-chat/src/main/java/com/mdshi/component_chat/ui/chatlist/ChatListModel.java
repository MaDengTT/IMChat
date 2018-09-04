package com.mdshi.component_chat.ui.chatlist;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.mdshi.common.db.dao.MessageDao;
import com.mdshi.component_chat.bean.ChatBean;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by MaDeng on 2018/9/4.
 */
public class ChatListModel extends AndroidViewModel implements ChatListContract.Model {

    MessageDao dao;
    private MutableLiveData<List<ChatBean>> data;
    @Inject
    public ChatListModel(Application app,MessageDao dao) {
        super(app);
        this.dao = dao;
    }

    public MutableLiveData<List<ChatBean>> getChatListData() {
        if (data == null) {
            data = new MutableLiveData<>();
        }
        return data;
    }
    @Override
    public void onDestroy() {
        dao.getMessageListAll();
    }
}
