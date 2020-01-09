package com.appv1.Plan4Success;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static com.appv1.Plan4Success.MainActivity.positionSpin;
import static com.appv1.Plan4Success.TabFragment1.mWordList;
import static com.appv1.Plan4Success.TabFragment3.mPreferences;


public class notifications extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {
    Spinner spinner;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private NotificationManager mNotifyManager;
    private static final int NOTIFICATION_ID = 0;
    public static SharedPreferences mPreferences;
    private String sharedPrefFile =    "com.example.android.Plan4Success";
    TextView cancel;
    TextView setReminder;
    public static boolean value = false;

    PendingIntent broadcast;
    AlarmManager alarmManager;
    int difference = 0;
    private AdView  mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        MobileAds.initialize(this);
        mAdView = findViewById(R.id.adView4);
        //mAdView.setAdSize(AdSize.BANNER);
        //mAdView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mPreferences = this.getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        //SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        String date = mPreferences.getString("time1a", "Set Time");
        setReminder = findViewById(R.id.SetDate);
        setReminder.setText(date);
        setReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getSupportFragmentManager(),"datePicker");
            }
        });
        /*cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmManager.cancel(broadcast);
            }
        });*/

// ... End of onCreate code ...
        spinner = findViewById(R.id.spinner);

        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.labels_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner.
        if (spinner != null) {
            spinner.setAdapter(adapter);
        }
        int i = mPreferences.getInt("position",-1);
        Log.d("MyActivity", "what" + i);
        if(mPreferences.getInt("position",-1)>=0){
            spinner.setSelection(mPreferences.getInt("position",0));
            String a = adapter.getItem(i).toString();
            Log.d("MyActivity", "position" + a);
                    //scheduleRepeatingElapsedNotification(this, a);
            //value = true;
            //recurring(a);

        }

        //createNotificationChannel();







    }
    public void update(int hour, int minute){
        String time = hour + " : "+minute;
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putString("time1a", time);
        preferencesEditor.apply();
        setReminder.setText(time);
    }
    public void recurring(String def){
        if(def.equals("None")){
            //repeat(1);
            try {
               WorkManager.getInstance().cancelUniqueWork("MyActivity");
            }catch (Exception NullPointerException){
                return;
            }

        }else if(def.equals("15")){
            repeat(15);
        }else if(def.equals("30")){
            repeat(30);
        }else if(def.equals("60")){
            repeat(60);

        }else if(def.equals("90")){
            repeat(90);
        }else if(def.equals("120")){
            repeat(120);
        }
    }

    PeriodicWorkRequest periodicWork;
    public void repeat(int difference){
         periodicWork = new PeriodicWorkRequest.Builder(NotifyWorker.class, difference, TimeUnit.MINUTES).build();
         Log.d("MyActivity", "ree" + difference);
        WorkManager.getInstance().enqueueUniquePeriodicWork("MyActivity", ExistingPeriodicWorkPolicy.KEEP, periodicWork);
        //WorkManager.getInstance().enqueue(periodicWork);
    }
    OneTimeWorkRequest mywork;
    public void runAt(int difference) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
             mywork =
                    new OneTimeWorkRequest.Builder(NotifyWorker.class)
                            .setInitialDelay( difference , TimeUnit.SECONDS)// Use this when you want to add initial delay or schedule initial work to `OneTimeWorkRequest` e.g. setInitialDelay(2, TimeUnit.HOURS)
        .build();
            WorkManager.getInstance().enqueueUniqueWork("mywork", ExistingWorkPolicy.KEEP, mywork);
        }
        else{
            Toast.makeText(this, "Need Android 8 or Higher", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        String spinnerLabel = adapterView.getItemAtPosition(position).toString();
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putInt("position", position);
        preferencesEditor.apply();

            //positionSpin = position;

        Log.d("MyActivity", "what2"+positionSpin);
        recurring(spinnerLabel);
       // scheduleRepeatingElapsedNotification(this, spinnerLabel);
        //displayToast(spinnerLabel);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void SetReminder(View view) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(),"datePicker");
    }
    public void CancelRemind(View view) {
        try {
            WorkManager.getInstance().cancelUniqueWork("mywork");
            setReminder.setText("Set Time");
            final SharedPreferences.Editor preferencesEditor = mPreferences.edit();
            preferencesEditor.putString("time1a", setReminder.getText().toString());
            preferencesEditor.apply();
        }catch (Exception NullPointerException){
            return;
        }
    }
    public void processTimePickerResult(int hourOfDay, int minute) {
        // Convert time elements into strings.
        String hour_string = Integer.toString(hourOfDay);
        String minute_string = Integer.toString(minute);
        // Assign the concatenated strings to timeMessage.
        String timeMessage = (hour_string + ":" + minute_string);
        int sec = (hourOfDay*60*60) + (minute*60);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        Date date = new Date();   // given date
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //cal.setTimeInMillis(System.currentTimeMillis());

        int seconds = (cal.get(Calendar.HOUR_OF_DAY)*60*60) + (cal.get(Calendar.MINUTE)*60)+cal.get(Calendar.SECOND);
         difference = Math.abs(sec-seconds);
        long time = cal.getTimeInMillis();
        //Toast.makeText(this,"Works "
                //+ difference, Toast.LENGTH_SHORT).show();



        //scheduleRepeatingElapsedNotification(this);
        //sendNotification(difference);
        runAt(difference);
        update(hourOfDay, minute);


    }
    public void sendNotification(int time) {
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent notificationIntent = new Intent(this, AlarmReceiver.class);
        notificationIntent.putExtra("size7", mWordList.size());
        broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar cal = Calendar.getInstance();
        //long difference = time - cal.getTimeInMillis();
        cal.add(Calendar.SECOND, time);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
        //Toast.makeText(this,"Works123 "+cal.getTimeInMillis(), Toast.LENGTH_SHORT).show();
    }
    public static final String CHANNEL_ID = "exampleServiceChannel";

    /*public void createNotificationChannel()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Example Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }*/
    public void startService () {
        String input = "Hi";

        Intent serviceIntent = new Intent(this, ExampleService.class);
        serviceIntent.putExtra("inputExtra", input);

        ContextCompat.startForegroundService(this, serviceIntent);
    }

    public void stopService () {
        Intent serviceIntent = new Intent(this, ExampleService.class);
        stopService(serviceIntent);
    }/*
    private NotificationCompat.Builder getNotificationBuilder(){
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle("You've been notified!")
                .setContentText("This is your notification text.")
                .setSmallIcon(R.drawable.ic_android);
        return notifyBuilder;
    }*/

    public static void scheduleRepeatingElapsedNotification(Context context, String time) {
        //Setting intent to class where notification will be handled
        Intent intent = new Intent(context, AlarmReceiver.class);

        //Setting pending intent to respond to broadcast sent by AlarmManager everyday at 8am
        PendingIntent alarmIntentElapsed = PendingIntent.getBroadcast(context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //getting instance of AlarmManager service
        AlarmManager alarmManagerElapsed = (AlarmManager)context.getSystemService(ALARM_SERVICE);

        //Inexact alarm everyday since device is booted up. This is a better choice and
        //scales well when device time settings/locale is changed
        //We're setting alarm to fire notification after 15 minutes, and every 15 minutes there on
        if(time.equals("None")){
            //alarmIntentElapsed.cancel();
            alarmManagerElapsed.cancel(alarmIntentElapsed);
            return;
        }
        if(time.equals("30")){
            alarmManagerElapsed.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
                    System.currentTimeMillis() + AlarmManager.INTERVAL_HALF_HOUR,
                    AlarmManager.INTERVAL_HALF_HOUR, alarmIntentElapsed);
        }
        if(time.equals("60")){
            alarmManagerElapsed.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_HOUR,
                    AlarmManager.INTERVAL_HOUR, alarmIntentElapsed);
        }
        if(time.equals("90")){
            alarmManagerElapsed.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_HALF_HOUR*3,
                    AlarmManager.INTERVAL_HALF_HOUR*3, alarmIntentElapsed);
        }
        if(time.equals("120")){
            alarmManagerElapsed.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_HOUR*2,
                    AlarmManager.INTERVAL_HOUR*2, alarmIntentElapsed);
        }

    }

    public void CancelRecurring(View view) {
        WorkManager.getInstance().cancelWorkById(periodicWork.getId());
            spinner.setSelection(0);
    }
}

