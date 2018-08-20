package com.bezzo.coreandroid.services;

import android.content.*;
import android.support.v4.content.*;

import com.bezzo.core.util.*;
import com.bezzo.coreandroid.R;
import com.bezzo.coreandroid.features.main.*;
import com.google.firebase.messaging.*;

/**
 * Created by bezzo on 21/02/18.
 */

public class MessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        NotificationUtils.createNotification(1, remoteMessage.getNotification().getTitle(),
                remoteMessage.getNotification().getBody(), this, MainActivity.class, R.mipmap.ic_launcher);

        if (remoteMessage.getData() != null){
            sendBroadcast(remoteMessage.getData().get("type"));
        }
    }

    private void sendBroadcast(String type) {
        Intent intent = new Intent();
        intent.setAction("NOTIFICATION");
        intent.putExtra("type", type);

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }
}
