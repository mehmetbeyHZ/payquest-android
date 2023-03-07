package com.payquestion.payquest.Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.payquestion.payquest.AppActivity;
import com.payquestion.payquest.MainActivity;
import com.payquestion.payquest.R;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        showNotification(remoteMessage);
    }

    private void showNotification(RemoteMessage remoteMessage) {
        NotificationHelper notificationHelper = new NotificationHelper(this);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
        Intent myIntent = new Intent(this, AppActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                myIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        if(remoteMessage.getNotification() != null)
        {
            nb.setContentIntent(pendingIntent)
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setAutoCancel(true)
                    .setContentText(remoteMessage.getNotification().getBody());
            notificationHelper.getManager().notify(1, nb.build());
        }
    }

}
