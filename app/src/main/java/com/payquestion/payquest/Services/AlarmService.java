package com.payquestion.payquest.Services;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.payquestion.payquest.AppActivity;
import com.payquestion.payquest.R;

public class AlarmService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
        Intent myIntent = new Intent(context, AppActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                0,
                myIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        nb.setContentIntent(pendingIntent)
                .setContentTitle(context.getString(R.string.notification_times_end_title))
                .setAutoCancel(true)
                .setContentText(context.getString(R.string.notification_times_end_desc));
        notificationHelper.getManager().notify(1, nb.build());
    }
}
