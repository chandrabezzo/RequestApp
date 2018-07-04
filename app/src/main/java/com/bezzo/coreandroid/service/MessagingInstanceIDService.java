package com.bezzo.coreandroid.service;

import com.bezzo.coreandroid.*;
import com.bezzo.coreandroid.di.component.*;
import com.bezzo.coreandroid.util.*;
import com.google.firebase.iid.*;

/**
 * Created by bezzo on 21/02/18.
 */

public class MessagingInstanceIDService extends FirebaseInstanceIdService {

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
