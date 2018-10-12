package com.mdshi.im.ui.search;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.text.TextUtils;

import com.mdshi.common.base.BaseBean;
import com.mdshi.common.db.entity.UserEntity;
import com.mdshi.common.vo.AbsentLiveData;
import com.mdshi.im.data.UserRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by MaDeng on 2018/10/12.
 */
public class SearchModel extends ViewModel {

    MutableLiveData<Search> search = new MutableLiveData<>();
    private final LiveData<BaseBean<List<UserEntity>>> searchData;



    @Inject
    public SearchModel(UserRepository repository) {

        searchData = Transformations.switchMap(search, data -> {
            if (data==null||TextUtils.isEmpty(data.search)) {
                return AbsentLiveData.create();
            } else {
                return repository.searchContacts(data.search,data.pagesize,data.pageno);
            }
        });
    }

    public LiveData<BaseBean<List<UserEntity>>> getSearchData() {
        return searchData;
    }

    public class Search {
        String search;
        int pagesize;
        int pageno;

        public Search next() {
            pageno++;
            return this;
        }

        public Search(String search) {
            this.search = search;
            this.pagesize = 20;
            this.pageno = 0;
        }
    }

    public void searchContacts(String s) {
        search.setValue(new Search(s));
    }

    public void searchNext() {
        search.setValue(search.getValue().next());
    }


}
