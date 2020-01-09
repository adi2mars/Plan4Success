package com.appv1.Plan4Success;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ExistingWorkPolicy;
import androidx.work.ListenableWorker;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import static com.appv1.Plan4Success.MainActivity.positionSpin;
import static com.appv1.Plan4Success.TabFragment3.mPreferences;

public class RecurringNotif1 extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {
    Spinner spinner;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private static final String Tag = "MyActivity";

    private NotificationManager mNotifyManager;
    private static final int NOTIFICATION_ID = 0;
    TextView Date2;
    TextView time;
    private DatePickerDialog.OnDateSetListener mDateSetListener2;
    private TimePickerDialog.OnTimeSetListener mDateSetListener3;
    public static int position2;
    //public static SharedPreferences mPreferences;
    private String sharedPrefFile =    "com.example.android.Plan4Success";

    PendingIntent broadcast2;
    AlarmManager alarmManager2;
    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recurring_notif1);
        MobileAds.initialize(this);
        mAdView = findViewById(R.id.adView5);
        //mAdView.setAdSize(AdSize.BANNER);
        //mAdView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        Date2 = findViewById(R.id.ReminderDate2a);
        time = findViewById(R.id.ReminderTime2a);
        //position2 = getIncomingIntegerIntent("position");
        mPreferences = this.getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        final SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        String timeDescrip = mPreferences.getString("time2a", "Set Time");
        String dateDescrip = mPreferences.getString("date2a", "Set Date");
        Date2.setText(dateDescrip);
        time.setText(timeDescrip);
        final Calendar cal2 = Calendar.getInstance();
        final int year2 = cal2.get(Calendar.YEAR);
        final int month2 = cal2.get(Calendar.MONTH);
        final int day2 = cal2.get(Calendar.DAY_OF_MONTH);

// ... End of onCreate code ...
        spinner = findViewById(R.id.spinner2a);
        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.labels_array2, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner.
        if (spinner != null) {
            spinner.setAdapter(adapter);
        }
        int i = mPreferences.getInt("position2",-1);
        Log.d("MyActivity", "what" + i);
        if(mPreferences.getInt("position2",-1)>=0){
            spinner.setSelection(mPreferences.getInt("position2",0));
            String a = adapter.getItem(i).toString();
            Log.d("MyActivity", "position" + a);
            //scheduleRepeatingElapsedNotification(this, a);
            //value = true;
            //recurring(a);

        }
        //spinner.setSelection(mWordList3.get(position2).positionSpinner);
        //Log.d(Tag,"posS" + mWordList3.get(position2).positionSpinner );
        //String a = adapter.getItem(mWordList2.get(position2).positionSpinner).toString();
        //scheduleRepeatingElapsedNotification(this, a);
        //recurring(a);



        //createNotificationChannel();
        Date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog dialog2 = new DatePickerDialog(
                        RecurringNotif1.this,
                        mDateSetListener2,
                        year2, month2, day2);
                //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog2.show();
            }
        });


        mDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year2, int month2, int day2) {

                month2 = month2 + 1;
                Log.d(Tag, "onDateSet: mm/dd/yyy: " + month2 + "/" + day2 + "/" + year2);

                String date2 = month2 + "/" + day2 + "/" + year2;
                Date2.setText(date2);
                preferencesEditor.putString("date2a", date2);
                preferencesEditor.apply();
                //sendNotification(cal2);
            }
        };
        time.setOnClickListener(new View.OnClickListener() {
            int hour = cal2.get(Calendar.HOUR_OF_DAY);
            int minute = cal2.get(Calendar.MINUTE);

            @Override
            public void onClick(View v) {
                TimePickerDialog dialog3 = new TimePickerDialog(RecurringNotif1.this, mDateSetListener3,
                        hour, minute, false);

                dialog3.show();
            }
        });
        mDateSetListener3 = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String Time = hourOfDay+"h " + minute + ("min");

                time.setText(Time);
                preferencesEditor.putString("time2a", Time);
                preferencesEditor.apply();
                cal2.set(year2, month2, day2, hourOfDay, minute, 0);
                //Toast.makeText(getApplicationContext(),"Works "
                      //  + cal2.get(Calendar.MINUTE), Toast.LENGTH_SHORT).show();
                //sendNotification(cal2);
                runAt(cal2);

            }
        };
        /*SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putInt("Position7", position2);
        preferencesEditor.apply();*/

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        String spinnerLabel = adapterView.getItemAtPosition(position).toString();
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putInt("position2", position);
        preferencesEditor.apply();

        //positionSpin = position;

        Log.d("MyActivity", "what2"+positionSpin);
        recurring(spinnerLabel);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void recurring(String def) {
        if (def.equals("None")) {
            //repeat(1);
            try {
                WorkManager.getInstance().cancelUniqueWork("MyActivity2a");

            } catch (Exception NullPointerException) {
                Log.d("MyActivity", "nullNotif");
                return;
            }

        } else if (def.equals("Every Day")) {
            repeat(24 * 60);
        } else if (def.equals("Every 2 Days")) {
            repeat(2 * 24 * 60);
        } else if (def.equals("Every Week")) {
            repeat(7 * 24 * 60);
        } else if (def.equals("15")) {
            repeat(15);
        } else if (def.equals("Every 2 Weeks")) {
            repeat(7 * 24 * 60 * 2);
        } else if (def.equals("Every 12 Hours")) {
            repeat(12 * 60);
        }
    }

    PeriodicWorkRequest periodicWork2a;
    public void repeat(int difference){
        periodicWork2a = new PeriodicWorkRequest.Builder(NotifyWorker2a.class, difference, TimeUnit.MINUTES)                .build();
        //WorkManager.getInstance().enqueue(periodicWork3);
        WorkManager.getInstance().enqueueUniquePeriodicWork("MyActivity2a", ExistingPeriodicWorkPolicy.KEEP, periodicWork2a);
        //WorkManager.getInstance().enqueue(periodicWork);
    }
    OneTimeWorkRequest mywork2a;

    public void runAt(Calendar cal) {
        Calendar calendar = Calendar.getInstance();
        long difference = cal.getTimeInMillis() - calendar.getTimeInMillis();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            mywork2a =
                    new OneTimeWorkRequest.Builder(NotifyWorker2a.class)
                            .setInitialDelay( difference , TimeUnit.MILLISECONDS)// Use this when you want to add initial delay or schedule initial work to `OneTimeWorkRequest` e.g. setInitialDelay(2, TimeUnit.HOURS)
                            .build();
            WorkManager.getInstance().enqueueUniqueWork("mywork2a", ExistingWorkPolicy.KEEP, mywork2a);
           /* while(difference > 0){
               difference = cal.getTimeInMillis() - calendar.getTimeInMillis();
               Log.d(Tag, "running" + difference);
            }*/
        }
        else{
            Toast.makeText(this, "Need Android 8 or Higher", Toast.LENGTH_SHORT).show();
        }
    }


    public void sendNotification(Calendar cal) {
        alarmManager2 = (AlarmManager) this.getSystemService(ALARM_SERVICE);

        Intent i = new Intent(this, AlarmReceiver3.class);
        broadcast2 = PendingIntent.getBroadcast(this, 101, i, PendingIntent.FLAG_UPDATE_CURRENT);
        //long difference = time - cal.getTimeInMillis();
        //cal = Calendar.getInstance();
        alarmManager2.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast2);
        //Toast.makeText(this,"Works123 "+cal.getTimeInMillis(), Toast.LENGTH_SHORT).show();
        Log.d(Tag,"fdfd" + cal.getTimeInMillis());
    }
    public static void scheduleRepeatingElapsedNotification(Context context, String time) {
        //Setting intent to class where notification will be handled
        //Setting intent to class where notification will be handled
        Intent intent = new Intent(context, AlarmReceiver3.class);

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
        if(time.equals("1")){
            alarmManagerElapsed.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime(),
                    AlarmManager.INTERVAL_FIFTEEN_MINUTES/15, alarmIntentElapsed);
        }
        if(time.equals("Every Week")){
            alarmManagerElapsed.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_DAY*7,
                    AlarmManager.INTERVAL_DAY*7, alarmIntentElapsed);
        }
        if(time.equals("Every 2 Weeks")){
            alarmManagerElapsed.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_DAY*14,
                    AlarmManager.INTERVAL_DAY*14, alarmIntentElapsed);
        }
        if(time.equals("Every Month")){
            alarmManagerElapsed.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_DAY*30,
                    AlarmManager.INTERVAL_DAY*30, alarmIntentElapsed);
        }
        if(time.equals("Every Year")){
            alarmManagerElapsed.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_DAY*365,
                    AlarmManager.INTERVAL_DAY*365, alarmIntentElapsed);
        }

    }
    private int getIncomingIntegerIntent(String descrip){
        Log.d(Tag, "getIncomingIntent: checking for incoming intents.");
        int taskName10 = 0;
        if(getIntent().hasExtra(descrip)){
            taskName10 = getIntent().getIntExtra(descrip,1);

            Log.d(Tag, "getIncomingIntent: found intent extras." + taskName10);

            //task.setText(taskName10);
        }
        return taskName10;
    }


    public void CancelRemind5(View view) {
        try {
            WorkManager.getInstance().cancelUniqueWork("mywork2a");
            time.setText("Set Time");
            Date2.setText("Set Date");
            final SharedPreferences.Editor preferencesEditor = mPreferences.edit();
            preferencesEditor.putString("date2a", Date2.getText().toString());
            preferencesEditor.putString("time2a", time.getText().toString());
            preferencesEditor.apply();
        }catch (Exception NullPointerException){
            return;
        }        Log.d("MyActivity", "cancelled " +WorkManager.getInstance().getLastCancelAllTimeMillis());

    }
}
