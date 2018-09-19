package com.mdshi.component_chat.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;


import java.security.Provider;
import java.util.Map;

import javax.inject.Singleton;

/**
 * Created by MaDeng on 2018/9/19.
 */
@Singleton
public class ViewModelFactory implements ViewModelProvider.Factory {

    private final Map<Class<?extends ViewModel>,Provider> creators;

    public ViewModelFactory(Map<Class<? extends ViewModel>, Provider> creators) {
        this.creators = creators;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        Provider creator = creators.get(modelClass);
        if(creator==null){
            for (Map.Entry<Class<?extends ViewModel>,Provider> entry:creators.entrySet()){
                if (modelClass.isAssignableFrom(entry.getKey())) {
                    creator = entry.getValue();
                    break;
                }
            }
        }
        if (creator == null) {
            throw new IllegalArgumentException("unknown model class " + modelClass);
        }
        try {
//            return (T) creator.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    // https://github.com/googlesamples/android-architecture-components/tree/master/GithubBrowserSample/app/src/main/java/com/android/example/github/di
    //  TODO https://github.com/googlesamples/android-architecture-components/issues/141
}
