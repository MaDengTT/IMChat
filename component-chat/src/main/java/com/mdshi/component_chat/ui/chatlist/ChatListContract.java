package com.mdshi.component_chat.ui.chatlist;

import android.arch.lifecycle.MutableLiveData;

import com.mdshi.common.mvp.BaseModel;
import com.mdshi.common.mvp.BasePresenter;
import com.mdshi.common.mvp.IModel;
import com.mdshi.common.mvp.IPresenter;
import com.mdshi.common.mvp.IView;
import com.mdshi.component_chat.bean.ChatBean;

import java.util.List;

/**
 * Created by MaDeng on 2018/9/4.
 */
public interface ChatListContract {

    interface View extends IView {
        void dataToView(List<ChatBean> data);
    }

    interface  Presenter {
    }

    interface Model extends IModel {
        MutableLiveData<List<ChatBean>> getChatListData();
    }


}
