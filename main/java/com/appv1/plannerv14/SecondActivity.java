package com.appv1.Plan4Success;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

public class SecondActivity extends Activity {
    EditText editview3;
    EditText editview2;
    EditText editview1;
    NumberPicker numberpicker;
    TextView priority;
    TextView time;
    TextView task;
    private static final String Tag = "MyActivity";
    Intent intent;
    private static final String Message = "get_photo";
    int position;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        task = (TextView) findViewById(R.id.AssignmentTitle);

        String message = getIncomingIntent("task");
        //String message3 = getIncomingIntent("priority");
        int message3 = getIncomingIntegerIntent("priority");
        int message4 = getIncomingIntegerIntent("time");
        String message2 = getIncomingIntent("index");
        editview1 = (EditText) findViewById(R.id.PriorityDef);
        if(message.equals("Enter in Task")){
            message = "";
            editview1.setText(message);
            Log.d(Tag, "clear");
        }else{
            editview1.setText(message);
        }
        editview1.setText(message);
        position = Integer.parseInt(message2);
        editview2 = (EditText) findViewById(R.id.DueDateDef);
        editview2.setText(Integer.toString(message3));
        editview3 = (EditText) findViewById(R.id.TimeDef);
        editview3.setText(Integer.toString(message4));
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
    }

    public void Save(View view) {
        String reply = editview1.getText().toString();
        String reply2 = editview2.getText().toString();
        String reply3 = editview3.getText().toString();
        Intent replyIntent = new Intent();
        replyIntent.putExtra("taskName123", reply);
        replyIntent.putExtra("priorityReturn", reply2);
        replyIntent.putExtra("timeReturn", reply3);

        replyIntent.putExtra("returnIndex", position);
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
}

