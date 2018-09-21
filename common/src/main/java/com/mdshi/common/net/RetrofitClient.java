package com.mdshi.common.net;

import android.content.Context;

import com.mdshi.common.net.decodefactory.DecodeConverterFactory;
import com.mdshi.common.net.decodefactory.LiveDataCallAdapterFactory;
import com.mdshi.common.utils.NetWorkUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by MaDeng on 2018/1/11.
 */

public class RetrofitClient {

    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJava2CallAdapterFactory.create();
    private static Converter.Factory decodeConverteFactory = DecodeConverterFactory.create();
    public static Retrofit mRetrofit;
    private static final String HTTP_CACHE_FILENAME = "Cache";
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;



    public static Retrofit createService(final Context context, String baseUrl) {
        if (mRetrofit == null) {
            File httpCacheDirectory = new File(context.getCacheDir().getAbsolutePath(), HTTP_CACHE_FILENAME);

            Cache cache;
            cache = new Cache(httpCacheDirectory, 100 * 1024 * 1024);

            Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    if (!NetWorkUtils.isNetConnected(context)) {
                        request = request.newBuilder()
                                .cacheControl(CacheControl.FORCE_CACHE)
                                .build();
                    }
                    Response originalResponse = chain.proceed(request);
                    if (NetWorkUtils.isNetConnected(context)) {
                        //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                        String cacheControl = request.cacheControl().toString();
                        return originalResponse.newBuilder()
                                .header("Cache-Control", cacheControl)
                                .removeHeader("Pragma")
                                .build();
                    } else {
                        return originalResponse.newBuilder()
                                .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC)
                                .removeHeader("Pragma")
                                .build();
                    }
                }
            };
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            Interceptor headerInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request build = chain.request().newBuilder()
                            .addHeader("Content-Type","application/json")
                            .build();
                    return chain.proceed(build);
                }
            };

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(7676, TimeUnit.MILLISECONDS)
                    .connectTimeout(7676,TimeUnit.MILLISECONDS)
                    .addInterceptor(mRewriteCacheControlInterceptor)
                    .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor(headerInterceptor)
                    .cache(cache)
                    .build();

            Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(getGsonConverterFactory())
                    .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                    .addCallAdapterFactory(getRxJavaCallAdapterFactory());

            mRetrofit = retrofitBuilder.client(okHttpClient)
                    .build();
        }
        return mRetrofit;
    }

    public static Converter.Factory getGsonConverterFactory() {
        return gsonConverterFactory;
//        return decodeConverteFactory; //加密
    }
    public static CallAdapter.Factory getRxJavaCallAdapterFactory() {
        return rxJavaCallAdapterFactory;
    }
}
