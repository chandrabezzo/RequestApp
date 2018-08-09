package com.bezzo.coreandroid.data.network;

import android.content.*;
import android.support.annotation.*;

import com.bezzo.coreandroid.util.constanta.*;

import java.util.concurrent.*;

import okhttp3.*;
import retrofit2.*;
import retrofit2.adapter.rxjava.*;
import retrofit2.converter.gson.*;

public class NetworkClient {

    @NonNull
    public static Retrofit create(Context context, String baseUrl){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(ApiConstans.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(ApiConstans.WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(ApiConstans.READ_TIMEOUT, TimeUnit.SECONDS)
                    .addNetworkInterceptor(new HttpInterceptor(context))
                    .build();

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }
}