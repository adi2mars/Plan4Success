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
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import static com.appv1.Plan4Success.MainActivity.positionSpin3;
import static com.appv1.Plan4Success.TabFragment2.mWordList2;
import static com.appv1.Plan4Success.TabFragment3.mPreferences;
import static com.appv1.Plan4Success.TabFragment3.mWordList3;

public class notifications2 extends AppCompatActivity implements
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications2);

        Date2 = findViewById(R.id.ReminderDate);
        time = findViewById(R.id.ReminderTime);
        position2 = getIncomingIntegerIntent("position");

        final Calendar cal2 = Calendar.getInstance();
        final int year2 = cal2.get(Calendar.YEAR);
        final int month2 = cal2.get(Calendar.MONTH);
        final int day2 = cal2.get(Calendar.DAY_OF_MONTH);

// ... End of onCreate code ...
        spinner = null;
        //spinner = findViewById(R.id.spinner2);
        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.labels_array3, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner.
        if (spinner != null) {
            spinner.setAdapter(adapter);
        }

        spinner.setSelection(mWordList3.get(position2).positionSpinner);
        Log.d(Tag,"posS" + mWordList3.get(position2).positionSpinner );
        String a = adapter.getItem(mWordList2.get(position2).positionSpinner).toString();
        //scheduleRepeatingElapsedNotification(this, a);
        //recurring(a);



        //createNotificationChannel();
        Date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog dialog2 = new DatePickerDialog(
                        notifications2.this,
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
                //sendNotification(cal2);
            }
        };
        time.setOnClickListener(new View.OnClickListener() {
            int hour = cal2.get(Calendar.HOUR_OF_DAY);
            int minute = cal2.get(Calendar.MINUTE);

            @Override
            public void onClick(View v) {
                TimePickerDialog dialog3 = new TimePickerDialog(notifications2.this, mDateSetListener3,
                        hour, minute, false);

                dialog3.show();
            }
        });
        mDateSetListener3 = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String Time = hourOfDay+"h " + minute + ("min");

                time.setText(Time);
                cal2.set(year2, month2, day2, hourOfDay, minute, 0);
                Toast.makeText(getApplicationContext(),"Works "
                        + cal2.get(Calendar.MINUTE), Toast.LENGTH_SHORT).show();
                //sendNotification(cal2);
                runAt(cal2);
            }
        };
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putInt("Position7", position2);
        preferencesEditor.apply();
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        String spinnerLabel = adapterView.getItemAtPosition(position).toString();
        positionSpin3 = position;
        mWordList3.set(position2, new Model3(mWordList3.get(position2).task3, mWordList3.get(position2).priority3,
                mWordList3.get(position2).time3, mWordList3.get(position2).SubGoal, position));
        //scheduleRepeatingElapsedNotification(this, spinnerLabel);
        recurring(spinnerLabel);
        //displayToast(spinnerLabel);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void recurring(String def){
        if(def.equals("None")){
            //repeat(1);
            try {
                WorkManager.getInstance().cancelUniqueWork("MyActivity3");
            }catch (Exception NullPointerException){
                return;
            }

        }else if(def.equals("15")){
            repeat(15);
        }else if(def.equals("Every Week")){
            repeat(24*7*60);
        }else if(def.equals("Every 2 Weeks")){
            repeat(24*7*60*2);

        }else if(def.equals("Every Month")){
            repeat(24*30*60);

        }else if(def.equals("Every Year")){
            repeat(24*365*60);

        }
    }

    PeriodicWorkRequest periodicWork3;
    public void repeat(int difference){
        periodicWork3 = new PeriodicWorkRequest.Builder(NotifyWorker3.class, difference, TimeUnit.MINUTES)                .build();
        //WorkManager.getInstance().enqueue(periodicWork3);
        WorkManager.getInstance().enqueueUniquePeriodicWork("MyActivity3", ExistingPeriodicWorkPolicy.KEEP, periodicWork3);
        //WorkManager.getInstance().enqueue(periodicWork);
    }
    OneTimeWorkRequest mywork3;

    public void runAt(Calendar cal) {
        Calendar calendar = Calendar.getInstance();
        long difference = cal.getTimeInMillis() - calendar.getTimeInMillis();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {


            mywork3 =
                    new OneTimeWorkRequest.Builder(NotifyWorker3.class)
                            .setInitialDelay( difference , TimeUnit.MILLISECONDS)// Use this when you want to add initial delay or schedule initial work to `OneTimeWorkRequest` e.g. setInitialDelay(2, TimeUnit.HOURS)
                            .build();
            WorkManager.getInstance().enqueue(mywork3);
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
            WorkManager.getInstance().cancelWorkById(mywork3.getId());
        }catch (Exception NullPointerException){
            return;
        }        Log.d("MyActivity", "cancelled " +WorkManager.getInstance().getLastCancelAllTimeMillis());

    }
}
