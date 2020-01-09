package com.appv1.Plan4Success;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import static com.appv1.Plan4Success.TabFragment3.mPreferences;
import static com.appv1.Plan4Success.TabFragment3.mWordList3;

public class SecondActivity3 extends AppCompatActivity {
    private String sharedPrefFile =    "com.example.android.Plan4Success";
        TextView editview3;
        EditText editview2;
        EditText editview1;
        NumberPicker numberpicker;
        TextView priority;
        TextView task;
        private static final String Tag = "MyActivity";
        Intent intent;
        private static final String Message = "get_photo";
        int position;
        public static LinkedList<String> mWordList4 = new LinkedList<String>();
        private RecyclerView mRecyclerView;
        public WordListAdapter4 mAdapter;
        private String SubGoals = "";
        private DatePickerDialog.OnDateSetListener mDateSetListener;
    final Calendar cal = Calendar.getInstance();
    final int year = cal.get(Calendar.YEAR);
    final int month = cal.get(Calendar.MONTH);
    final int day = cal.get(Calendar.DAY_OF_MONTH);
    TextView Date2;
    TextView time;
    private DatePickerDialog.OnDateSetListener mDateSetListener2;
    private TimePickerDialog.OnTimeSetListener mDateSetListener3;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second3);
        //task = (TextView) findViewById(R.id.AssignmentTitle3);

        String message = getIncomingIntent("task3");
        //String message3 = getIncomingIntent("priority");
        int message3 = getIncomingIntegerIntent("priority3");
        String message4 = getIncomingIntent("time3");
        String message2 = getIncomingIntent("index3");
        String subG = getIncomingIntent("subG");
        mWordList4.clear();
        String [] a = subG.split(System.getProperty("line.separator"));
        for(int i =0;i<a.length;i++){
            mWordList4.add(i, a[i]);
        }
        final Calendar cal2 = Calendar.getInstance();
        final int year2 = cal2.get(Calendar.YEAR);
        final int month2 = cal2.get(Calendar.MONTH);
        final int day2 = cal2.get(Calendar.DAY_OF_MONTH);
        editview1 = (EditText) findViewById(R.id.TaskDef3);
        if(message.equals("Enter in Goal")){
            message = "";
            editview1.setText(message);
            Log.d(Tag, "clear");
        }else{
            editview1.setText(message);
        }
        //editview1.setText(message);
        position = Integer.parseInt(message2);
        editview2 = (EditText) findViewById(R.id.PriorityDef3);
        editview2.setText(Integer.toString(message3));
        editview3 =  findViewById(R.id.TimeDef3);
        editview3.setText(message4);
        final SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putInt("Position7", position);
        preferencesEditor.apply();
        Date2 = findViewById(R.id.SetDate3);
        time = findViewById(R.id.setTime3);
        String date = mPreferences.getString("date"+position, "Set Date");
        String timeData = mPreferences.getString("time"+position, "Set Time");
        Date2.setText(date);
        time.setText(timeData);
        editview3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(
                        SecondActivity3.this,
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
                editview3.setText(date);
            }
        };
            mRecyclerView = findViewById(R.id.recyclerView4);
// Create an adapter and supply the data to be displayed.
            //mAdapter = new WordListAdapter4(this, mWordList4);

            mAdapter = new WordListAdapter4(this, mWordList4);

// Connect the adapter with the RecyclerView.
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mAdapter.setOnItemClickListener(new WordListAdapter4.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    //int mPosition = mAdapter.getAdapterPosition();

                    // Use that to access the affected item in mWordList.
                    //String element = mWordList.get(position).task;
                    // Change the word in the mWordList.

                    //mWordList.set(position, "Clicked! " + element);
                    // Notify the adapter, that the data has changed so it can
                    // update the RecyclerView to display the data.
                    //mAdapter.notifyDataSetChanged();
                }

                @Override
                public void onDeleteClick(int position) {

                    mWordList4.remove(position);
                   // mRecyclerView.removeViewAt(position);
                    //Log.d(Tag, "Adi123");
                    mAdapter.notifyItemRemoved(position);
                    mAdapter.notifyItemRangeChanged(position, mWordList4.size());
                    mAdapter.notifyDataSetChanged();


                    //finish();
                    //startActivity(getIntent());

                }
            });

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
        //mPreferences = getApplicationContext().getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        Date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog dialog2 = new DatePickerDialog(
                        SecondActivity3.this,
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
                preferencesEditor.putString("date"+position, date2);
                preferencesEditor.apply();

                //sendNotification(cal2);
            }
        };
        time.setOnClickListener(new View.OnClickListener() {
            int hour = cal2.get(Calendar.HOUR_OF_DAY);
            int minute = cal2.get(Calendar.MINUTE);

            @Override
            public void onClick(View v) {
                TimePickerDialog dialog3 = new TimePickerDialog(SecondActivity3.this, mDateSetListener3,
                        hour, minute, false);

                dialog3.show();
            }
        });
        mDateSetListener3 = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String Time = hourOfDay+"h " + minute + ("min");

                time.setText(Time);
                preferencesEditor.putString("time"+position, Time);
                preferencesEditor.apply();
                cal2.set(year2, month2, day2, hourOfDay, minute, 0);
                //Toast.makeText(getApplicationContext(),"Works "
                        //+ cal2.get(Calendar.MINUTE), Toast.LENGTH_SHORT).show();
                //sendNotification(cal2);
                runAt(cal2);
            }
        };
        /*SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putInt("Position7", position2);
        preferencesEditor.apply();*/
    }
    OneTimeWorkRequest mywork3;
    Data imageData;

    public void runAt(Calendar cal) {
        Calendar calendar = Calendar.getInstance();
        long difference = cal.getTimeInMillis() - calendar.getTimeInMillis();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            imageData =
                    new Data.Builder()
                            .putString("goal", mWordList3.get(position).task3)
                            .putString("time", mWordList3.get(position).time3)
                            .putInt("index", position+10)
                            .build();

            mywork3 =
                    new OneTimeWorkRequest.Builder(NotifyWorker3.class)
                            .setInputData(imageData)
                            .setInitialDelay( difference , TimeUnit.MILLISECONDS)// Use this when you want to add initial delay or schedule initial work to `OneTimeWorkRequest` e.g. setInitialDelay(2, TimeUnit.HOURS)
                            .build();
            WorkManager.getInstance().enqueueUniqueWork("mywork3"+position, ExistingWorkPolicy.KEEP, mywork3);
        }
        else{
            Toast.makeText(this, "Need Android 8 or Higher", Toast.LENGTH_SHORT).show();
        }
    }




    public void Save(View view) {
        String reply = editview1.getText().toString();
        String reply2 = editview2.getText().toString();
        String reply3 = editview3.getText().toString();
        Intent replyIntent = new Intent();
        replyIntent.putExtra("taskName3", reply);
        replyIntent.putExtra("priorityReturn3", reply2);
        replyIntent.putExtra("timeReturn3", reply3);
           // SubGoals = SubGoals.replace("\\n", System.getProperty("line.separator"));

            for(int i=0;i<mWordList4.size();i++) {
                if (i == mWordList4.size() - 1) {
                    SubGoals = SubGoals + mWordList4.get(i);

                } else {
                    SubGoals = SubGoals + mWordList4.get(i) + System.getProperty("line.separator");
                }
            }
            replyIntent.putExtra("subGoal", SubGoals);

            replyIntent.putExtra("returnIndex3", position);
        setResult(RESULT_OK,replyIntent);
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

    public void addSub(View view) {
            mWordList4.add(mWordList4.size(), "Enter SubGoal");
        mRecyclerView.getAdapter().notifyItemInserted(mWordList4.size());
        // Scroll to the bottom.
        mRecyclerView.smoothScrollToPosition(mWordList4.size());

    }

    public void Notification(View view) {
        Intent intent = new Intent(this, notifications2.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    public void CancelReminder(View view) {
        try {
            WorkManager.getInstance().cancelUniqueWork("mywork3"+position);
            Log.d("MyActivity", "cancelled");
            Date2.setText("Set Date");
            time.setText("Set Time");
            final SharedPreferences.Editor preferencesEditor = mPreferences.edit();
            preferencesEditor.putString("date"+position, Date2.getText().toString());
            preferencesEditor.putString("time"+position, time.getText().toString());
            preferencesEditor.apply();

        }catch (Exception NullPointerException){
            return;
        }
    }
}

