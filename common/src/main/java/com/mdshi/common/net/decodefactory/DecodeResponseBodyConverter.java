package com.mdshi.common.net.decodefactory;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by MaDeng on 2017/4/11.
 */

public class DecodeResponseBodyConverter<T> implements Converter<ResponseBody,T> {
    private final TypeAdapter<T> adapter;
    private final Gson gson;
    public DecodeResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.adapter = adapter;
        this.gson = gson;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        //解密字符串

        String keys = "ff06d6135e6b22dcaefd63aa497a964d";   //密钥
        String s = CryptAES.AES_Decrypt(keys, value.string());
        return adapter.fromJson(s);
    }

}
