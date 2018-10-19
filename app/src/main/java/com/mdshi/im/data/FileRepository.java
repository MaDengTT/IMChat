package com.mdshi.im.data;

import com.mdshi.common.base.BaseBean;
import com.mdshi.common.utils.FileUtils;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by MaDeng on 2018/10/19.
 */
public class FileRepository {

    UserService service;

    @Inject
    public FileRepository(UserService service) {
        this.service = service;
    }

    public Flowable<BaseBean<String>> uploadFile(String fileUri){
        File file = FileUtils.getFileByPath(fileUri);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        return service.upload(body);
    }
}
