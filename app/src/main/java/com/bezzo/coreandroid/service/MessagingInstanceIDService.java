package com.bezzo.coreandroid.service;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndJSONObjectRequestListener;
import com.bezzo.coreandroid.MvpApp;
import com.bezzo.coreandroid.data.DataManagerContract;
import com.bezzo.coreandroid.di.component.DaggerServiceComponent;
import com.bezzo.coreandroid.di.component.ServiceComponent;
import com.bezzo.coreandroid.util.AppLogger;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.Response;

/**
 * Created by bezzo on 21/02/18.
 */

public class MessagingInstanceIDService extends FirebaseInstanceIdService {

    @Inject
    DataManagerContract mDataManager;

    @Override
    public void onCreate() {
        super.onCreate();

        ServiceComponent component = DaggerServiceComponent.builder()
                .applicationComponent(((MvpApp) getApplication()).getComponent())
                .build();
        component.inject(this);
    }

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        AppLogger.d("Refresh Firebase Token : "+ refreshedToken);

        sendRegistrationToServer(refreshedToken);
    }

    public void sendRegistrationToServer(String refreshedToken){
        // request to api for refreshed token
    }
}
