package com.appv1.Plan4Success;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

import static android.content.Context.MODE_PRIVATE;
import static com.appv1.Plan4Success.TabFragment1.mPreferences;

public class NotifyWorker3a extends Worker {
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    public SharedPreferences mPreferences5;
    private String sharedPrefFile =    "com.example.android.Plan4Success";


    public NotifyWorker3a(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        // Method to trigger an instant notification
        sendNotification2();
        Log.d("MyActivity", "notif sent");

        return Result.success();
        // (Returning RETRY tells WorkManager to try this task again
        // later; FAILURE says not to try again.)

    }
    private static final String ACTION_UPDATE_NOTIFICATION =
            "com.android.example.notifyme.ACTION_UPDATE_NOTIFICATION";
    public void sendNotification2( ) {
        //alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

       /* Intent notificationIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
        notificationIntent.putExtra("size7", mWordList.size());
        PendingIntent broadcast2 = PendingIntent.getBroadcast(getApplicationContext(), 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
*/
        //Calendar cal = Calendar.getInstance();
        //long difference = time - cal.getTimeInMillis();
        //cal.add(Calendar.SECOND, time);
        //alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
        //Toast.makeText(this,"Works123 "+cal.getTimeInMillis(), Toast.LENGTH_SHORT).show();
        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
        //notificationIntent.putExtra("ID", mWordList.size());
        PendingIntent notificationPendingIntent = PendingIntent.getActivity
                (getApplicationContext(), 0, notificationIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
        mPreferences = getApplicationContext().getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        int a = mPreferences.getInt("SizeC", 0);

        String description = "";
        for(int i = 0; i<a;i++) {
            if (i != (a - 1)) {
                description = description + mPreferences.getString("Goal"+i,"") + ", " ;
            }else{
                description = description + mPreferences.getString("Goal"+i,"");

            }
        }
        Notification notification = builder
                .setContentTitle("You have " + a + " Goal(s) left")
                .setContentText(description)
                .setTicker("New Message Alert!")
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentIntent(notificationPendingIntent).build();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(PRIMARY_CHANNEL_ID);
        }

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

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
            Log.d("MyActivity", "notification coming");
        }

        notificationManager.notify(3, notification);
        Log.d("MyActivity", "notification");
    }


}