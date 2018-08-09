package com.bezzo.coreandroid.data.network;

import android.content.*;

import com.bezzo.coreandroid.*;
import com.bezzo.coreandroid.data.session.*;
import com.bezzo.coreandroid.util.*;

import java.io.*;

import javax.inject.*;

import okhttp3.*;

public class HttpInterceptor implements Interceptor {

    @Inject
    SessionHelperContract sessionHelper;

    Context context;

    public HttpInterceptor(Context context){
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl url = original.url();

        AppLogger.i(String.format(context.getString(R.string.log_encoded_path), url.encodedPath()));

        String token = sessionHelper.getSession(SessionConstants.TOKEN, "");

        Request.Builder requestBuilder = original.newBuilder()
                    .addHeader(SessionConstants.TOKEN, token);

        Request request = requestBuilder.build();

        long t1 = System.nanoTime();
        AppLogger.i(String.format(context.getString(R.string.log_sending_response),
                request.url(), chain.connection(), request.headers()));

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();
        AppLogger.i(String.format(context.getString(R.string.log_receive_response),
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));

        return response;
    }
}
