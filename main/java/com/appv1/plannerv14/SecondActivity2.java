package com.appv1.Plan4Success;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
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
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import static com.appv1.Plan4Success.TabFragment2.mWordList2;

public class SecondActivity2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    EditText editview3;
    TextView Date;
    TextView Date2;
    EditText editview1;
    NumberPicker numberpicker;
    TextView priority;
    TextView time;
    TextView task;
    private static final String Tag = "MyActivity";
    Intent intent;
    private static final String Message = "get_photo";
    public static int position5;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private DatePickerDialog.OnDateSetListener mDateSetListener2;
    private TimePickerDialog.OnTimeSetListener mDateSetListener3;
    public static SharedPreferences mPreferences;
    private String sharedPrefFile =    "com.example.android.Plan4Success";
    public int positonSpin;
    Context context;
    AlarmReceiver2 alarmReceiver2;
    PendingIntent broadcast;
    AlarmManager alarmManager;
    String a= "";






    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second2);
        mPreferences = this.getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        String message = getIncomingIntent("task2");
        final SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        //String message3 = getIncomingIntent("priority");
        String message3 = getIncomingIntent("priority2");
        String message2 = getIncomingIntent("index2");
        editview1 = (EditText) findViewById(R.id.AssignmentDef);
        Log.d(Tag, "ronit" + message);
        if(message.equals("Enter in Task")){
            message = "";
            editview1.setText(message);
            Log.d(Tag, "clear");
        }else{
            editview1.setText(message);
        }
        //editview1.setText(message);
        position5 = Integer.parseInt(message2);
         a = "MyActivity2"+position5;

        Date =  findViewById(R.id.DueDateDef);
        Date2 = findViewById(R.id.SetDate);
        time = findViewById(R.id.SetTime);
        String date = TabFragment3.mPreferences.getString("date2"+position5, "Set Date");
        String timeData = TabFragment3.mPreferences.getString("time2"+position5, "Set Time");
        Date2.setText(date);
        time.setText(timeData);
        Date.setText(message3);
        final Calendar cal = Calendar.getInstance();
        final int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        final int day = cal.get(Calendar.DAY_OF_MONTH);
        final Calendar cal2 = Calendar.getInstance();
        final int year2 = cal2.get(Calendar.YEAR);
        final int month2 = cal2.get(Calendar.MONTH);
        final int day2 = cal2.get(Calendar.DAY_OF_MONTH);
        PeriodicWorkRequest periodicWork2;

        Spinner spinner = null;
        //Spinner spinner = findViewById(R.id.spinner2);
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
        if(spinner!=null) {
            spinner.setSelection(mWordList2.get(position5).positionSpinner);
            String a = adapter.getItem(mWordList2.get(position5).positionSpinner).toString();
            //scheduleRepeatingElapsedNotification(this, a);
            //recurring(a);
        }


        final boolean is24Hourview = true;
        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog dialog = new DatePickerDialog(
                        SecondActivity2.this,
                        mDateSetListener,
                        year,month,day);
                //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(Tag, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                Date.setText(date);
            }
        };
        Date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog dialog2 = new DatePickerDialog(
                        SecondActivity2.this,
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
                        preferencesEditor.putString("date2"+position5, date2);
                        preferencesEditor.apply();
                        //sendNotification(cal2);
                    }
                };
                time.setOnClickListener(new View.OnClickListener() {
                     int hour = cal2.get(Calendar.HOUR_OF_DAY);
                     int minute = cal2.get(Calendar.MINUTE);

                    @Override
                    public void onClick(View v) {
                        TimePickerDialog dialog3 = new TimePickerDialog(SecondActivity2.this, mDateSetListener3,
                                hour, minute, false);

                        dialog3.show();
                    }
                });
                mDateSetListener3 = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String Time = hourOfDay+"h " + minute + ("min");

                        time.setText(Time);
                        preferencesEditor.putString("time2"+position5, Time);
                        preferencesEditor.apply();
                        cal2.set(year2, month2, day2, hourOfDay, minute, 0);
                        //Toast.makeText(getApplicationContext(),"Works "
                               // + cal2.get(Calendar.MINUTE), Toast.LENGTH_SHORT).show();
                        //sendNotification(cal2);
                        runAt(cal2);
                    }
                };

        //priority = (TextView) findViewById(R.id.priorities);
        //priority.setText(Integer.toString(message3));
        //time = (TextView) findViewById(R.id.timeManage);
        //time.setText(message4);
        //DisplayTime = (TextView)findViewById(R.id.textView1);

        /*textview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Dialog d = new Dialog(SecondActivity.this);
                d.setTitle("NumberPicker");
                d.setContentView(R.layout.activity_second);
                Button b1 = (Button) d.findViewById(R.id.button1);
                Button b2 = (Button) d.findViewById(R.id.button2);
                final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
                np.setMaxValue(100);
                np.setMinValue(0);
                np.setWrapSelectorWheel(false);
                np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {


                        textview.setText("Selected Value is : " + newVal);
                    }
                });
                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        textview.setText(String.valueOf(np.getValue()));
                        d.dismiss();
                    }
                });
                b2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                    }
                });
                d.show();


            }


        });*/
        preferencesEditor.putInt("Position5", position5);
        preferencesEditor.apply();
    }

    public void Save(View view) {
        String reply = editview1.getText().toString();
        String reply2 = Date.getText().toString();
        Intent replyIntent2 = new Intent();
        replyIntent2.putExtra("taskName2", reply);
        replyIntent2.putExtra("priorityReturn2", reply2);
        replyIntent2.putExtra("returnIndex2", position5);
        setResult(RESULT_OK,replyIntent2);
        finish();




    }
    private String getIncomingIntent(String descrip){
        Log.d(Tag, "getIncomingIntent: checking for incoming intents.");
        String taskName10 = "";
        if(getIntent().hasExtra(descrip)){
            taskName10 = getIntent().getStringExtra(descrip);

            Log.d(Tag, "getIncomingIntent: found intent extras." + taskName10);

            //task.setText(taskName10);
        }
        return taskName10;
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

    public void Cancel(View view) {
        finish();
    }
    /*public void processTimePickerResult(int month, int day, int year) {
        // Convert time elements into strings.
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        java.util.Date date = new Date();   // given date
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //cal.setTimeInMillis(System.currentTimeMillis());
        int
        int seconds = (cal.get(Calendar.HOUR_OF_DAY)*60*60) + (cal.get(Calendar.MINUTE)*60)+cal.get(Calendar.SECOND);
        int difference = Math.abs(sec-seconds);
        long time = cal.getTimeInMillis();
        Toast.makeText(this,"Works "
                + difference, Toast.LENGTH_SHORT).show();



        //scheduleRepeatingElapsedNotification(this);
        sendNotification(difference);

    }*/
    public void sendNotification(Calendar cal) {
         alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent notificationIntent = new Intent(this, AlarmReceiver2.class);
         broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //long difference = time - cal.getTimeInMillis();
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
        //Toast.makeText(this,"Works123 "+cal.getTimeInMillis(), Toast.LENGTH_SHORT).show();
    }
    /*public void createNotificationChannel()
    {
        mNotifyManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
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
            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }
    private NotificationCompat.Builder getNotificationBuilder(){
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle("You've been notified!")
                .setContentText("This is your notification text.")
                .setSmallIcon(R.drawable.ic_android);
        return notifyBuilder;
    }*/

    public static void scheduleRepeatingElapsedNotification(Context context, String time) {
        Intent intent = new Intent(context, AlarmReceiver2.class);

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
        if(time.equals("Every Day")){
            alarmManagerElapsed.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_DAY,
                    AlarmManager.INTERVAL_DAY, alarmIntentElapsed);
        }
        if(time.equals("Every 2 Days")){
            alarmManagerElapsed.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_DAY*2,
                    AlarmManager.INTERVAL_DAY*2, alarmIntentElapsed);
        }
        if(time.equals("Every Week")){
            alarmManagerElapsed.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_DAY*7,
                    AlarmManager.INTERVAL_DAY*7, alarmIntentElapsed);
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String spinnerLabel = parent.getItemAtPosition(position).toString();
        mWordList2.set(position5, new Model2(mWordList2.get(position5).Assignment,
                mWordList2.get(position5).DueDate, position));
        //scheduleRepeatingElapsedNotification(this, spinnerLabel);
        recurring(spinnerLabel);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void recurring(String def){
        if(def.equals("None")){
            //repeat(1);
            try {
                WorkManager.getInstance().cancelUniqueWork("MyAcitivity"+position5);
            }catch (Exception NullPointerException){
                return;
            }

        }else if (def.equals("Every Day")){
            repeat(24*60);
        }else if (def.equals("Every 2 Days")){
            repeat(2*24*60);
        }else if (def.equals("Every Week")){
            repeat(7*24*60);
        }else if(def.equals("15")){
            repeat(15);
        }
    }



    PeriodicWorkRequest periodicWork;
    PeriodicWorkRequest periodicWork2;


    public void repeat(int difference){
        if(position5 == 0){
            periodicWork= new PeriodicWorkRequest.Builder(NotifyWorker2.class, difference, TimeUnit.MINUTES)                .build();
            WorkManager.getInstance().enqueueUniquePeriodicWork("MyAcitivity"+position5, ExistingPeriodicWorkPolicy.KEEP, periodicWork);
            //WorkManager.getInstance().enqueue(periodicWork2);/
            Log.d("MyActivity", "name: " + a + "   "+ position5);
        }else{
            periodicWork2 = new PeriodicWorkRequest.Builder(NotifyWorker2.class, difference, TimeUnit.MINUTES)                .build();
            WorkManager.getInstance().enqueueUniquePeriodicWork("MyAcitivity"+position5, ExistingPeriodicWorkPolicy.KEEP, periodicWork2);
            //WorkManager.getInstance().enqueue(periodicWork2);/
            Log.d("MyActivity", "name: " + a + "   "+ position5);
        }
        /*mWordList2.get(position5).periodicWork = new PeriodicWorkRequest.Builder(NotifyWorker2.class, difference, TimeUnit.MINUTES)                .build();
        WorkManager.getInstance().enqueueUniquePeriodicWork(a, ExistingPeriodicWorkPolicy.KEEP, mWordList2.get(position5).periodicWork);
        //WorkManager.getInstance().enqueue(periodicWork2);/
        Log.d("MyActivity", "name: " + a + "   "+ position5);*/
    }
    OneTimeWorkRequest mywork2;
    Data descrip;
    int counter = 0;
    public void runAt(Calendar cal) {
        Calendar calendar = Calendar.getInstance();
        long difference = cal.getTimeInMillis() - calendar.getTimeInMillis();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            descrip =
                    new Data.Builder()
                            .putString("task", mWordList2.get(position5).Assignment)
                            .putString("time2", mWordList2.get(position5).DueDate)
                            .putInt("index2", position5+100)
                            .build();


            mywork2 =
                    new OneTimeWorkRequest.Builder(NotifyWorker2.class)
                            .setInputData(descrip)
                            .setInitialDelay( difference , TimeUnit.MILLISECONDS)// Use this when you want to add initial delay or schedule initial work to `OneTimeWorkRequest` e.g. setInitialDelay(2, TimeUnit.HOURS)
                            .build();
            WorkManager.getInstance().enqueueUniqueWork("mywork2"+position5, ExistingWorkPolicy.KEEP, mywork2);
        }
        else{
            Toast.makeText(this, "Need Android 8 or Higher", Toast.LENGTH_SHORT).show();
        }
    }



    public void CancelRemind2(View view) {
       // alarmManager.cancel(broadcast);
        try {
            WorkManager.getInstance().cancelUniqueWork("mywork2"+position5);
            Date2.setText("Set Date");
            time.setText("Set Time");
            final SharedPreferences.Editor preferencesEditor = mPreferences.edit();
            preferencesEditor.putString("date2"+position5, Date2.getText().toString());
            preferencesEditor.putString("time2"+position5, time.getText().toString());
            preferencesEditor.apply();

        }catch (Exception NullPointerException){
            return;
        }


    }
}


