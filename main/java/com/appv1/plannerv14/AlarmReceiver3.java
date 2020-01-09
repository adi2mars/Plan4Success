package com.appv1.Plan4Success;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;


import static com.appv1.Plan4Success.TabFragment3.mWordList3;
import static com.appv1.Plan4Success.notifications2.position2;

public class AlarmReceiver3 extends BroadcastReceiver {
    private static final String PRIMARY_CHANNEL_ID = "secondary_notification_channel";
    private static final String Tag = "MyActivity";


    @Override
    public void onReceive(Context context, Intent intent) {
        Intent notificationIntent = new Intent(context, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(notifications2.class);
        stackBuilder.addNextIntent(notificationIntent);
        Log.d(Tag, "arrived5");
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(context);
        String description = "";
        String sub = "";

        Notification notification = builder
                .setContentTitle("A Reminder About Your Goal: " + mWordList3.get(position2).task3)
                .setContentText("Estimated Date Of Completion " + mWordList3.get(position2).time3)
                .setTicker("New Message Alert!")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent).build();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(PRIMARY_CHANNEL_ID);
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {
            // Create a NotificationChannel
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    "Mascot Notification", NotificationManager
                    .IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from Mascot");
            notificationManager.createNotificationChannel(notificationChannel);
            Log.d(Tag, "notification coming");
        }

        notificationManager.notify(0, notification);
        Log.d(Tag, "notification");
    }

}


