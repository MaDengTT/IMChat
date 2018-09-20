package com.mdshi.common.base;

import android.support.v4.app.Fragment;

import com.mdshi.common.di.Injectable;
import com.mdshi.common.di.component.AppComponent;

/**
 * Created by MaDeng on 2018/8/31.
 */
public class BaseFragment extends Fragment implements Injectable {
//    protected AppComponent getAppComponent(){
//        if(getActivity().getApplication() instanceof BaseApplication){
//            return ((BaseApplication)(getActivity().getApplication())).AppComponent();
//        }else {
//            return null;
//        }
//    }
}
