package com.appv1.Plan4Success;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.appv1.Plan4Success;
import com.google.android.gms.ads.MobileAds;


import java.util.Collections;

import static com.appv1.Plan4Success.TabFragment1.mPreferences;
import static com.appv1.Plan4Success.TabFragment1.mWordList;
import static com.appv1.Plan4Success.TabFragment2.mWordList2;
import static com.appv1.Plan4Success.TabFragment3.mWordList3;
import static com.appv1.Plan4Success.WordListAdapter.TEXT_REQUEST;
//import static com.example.Plan4Success.notifications.Value;
//import static com.example.Plan4Success.notifications.positionSpin;

public class MainActivity extends AppCompatActivity {
    private static final String Tag = "MyActivity";
    ViewPager viewPager;
    TabLayout tabLayout;
    String reply;
    public static String SubGoals = "";
    public static int positionSpin=-1;
    public static int positionSpin2=-1;

    public static int positionSpin3=-1;

    public static boolean Value2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_label1));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_label2));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_label3));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager = findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        // Setting a listener for clicks.
        viewPager.addOnPageChangeListener(new
                TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, About.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addNotification(View view) {
        Intent intent = new Intent(this, notifications.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        //SharedPreferences.Editor preferencesEditor2 = mPreferences.edit();

        // ...
        //preferencesEditor.putInt("Size", mWordList.size());
        for (int i = 0; i < mWordList.size(); i++) {
            preferencesEditor.putInt("Size", mWordList.size());
            preferencesEditor.putInt("Priority" + i, mWordList.get(i).priority);
            preferencesEditor.putString("Task" + i, mWordList.get(i).task);
            preferencesEditor.putInt("Ti" + i, mWordList.get(i).time);
            preferencesEditor.apply();
        }
        preferencesEditor.putInt("SizeB", mWordList2.size());
        Log.d(Tag, "savedb" + mWordList2.size());
        for (int i = 0; i < mWordList2.size(); i++) {
            preferencesEditor.putString("Assignment" + i, mWordList2.get(i).Assignment);
            preferencesEditor.putString("Date" + i, mWordList2.get(i).DueDate);
            preferencesEditor.putInt("PositionSpin" + i, mWordList2.get(i).positionSpinner);
            preferencesEditor.putInt("notifInd" + i, i+1);
            preferencesEditor.apply();

        }
        preferencesEditor.putInt("SizeC", mWordList3.size());
        Log.d(Tag, "savedb" + mWordList3.size());
        for (int i = 0; i < mWordList3.size(); i++) {
            preferencesEditor.putString("Goal" + i, mWordList3.get(i).task3);
            preferencesEditor.putString("Approx" + i, mWordList3.get(i).time3);
            preferencesEditor.putInt("Priority3" + i, mWordList3.get(i).priority3);
            preferencesEditor.putString("SubG" + i, mWordList3.get(i).SubGoal);
            preferencesEditor.putInt("PositionSpin2" + i, mWordList3.get(i).positionSpinner);
            preferencesEditor.putInt("notifInd2" + i, i+100);
            preferencesEditor.apply();

        }


        if(positionSpin3!=-1){
            preferencesEditor.putInt("PositionSpin3", positionSpin3);
            preferencesEditor.apply();

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        //txtView = (TextView)findViewById(R.id.word);
        switch (requestCode) {
            case TEXT_REQUEST:
                if (resultCode == RESULT_OK) {
                    reply = data.getStringExtra("taskName123");
                    String priority = data.getStringExtra("priorityReturn");
                    String time = data.getStringExtra("timeReturn");

                    int index = data.getIntExtra("returnIndex", 0);
                    Log.d(Tag, "reply: " + index);
                    //mWordList.remove(index);
                    mWordList.set(index, new Model(reply, Integer.parseInt(priority), Integer.parseInt(time)));
                    //mIntentListener.onIntent(reply, index);
                    Collections.sort(mWordList);
                    finish();
                    startActivity(getIntent());
                    //txtView.setText(reply);
                    //mAdapter.onAct
                    //WordListAdapter.wordItemView.setText(reply);
                    //mRecyclerView.onActivityResult(requestCode, resultCode, data)//wordItemView.setText(reply);
                    //LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    String reply2 = data.getStringExtra("taskName2");
                    String dd = data.getStringExtra("priorityReturn2");
                    ;
                    int index = data.getIntExtra("returnIndex2", 0);
                    Log.d(Tag, "reply: " + index);
                    //mWordList.remove(index);
                    mWordList2.set(index, new Model2(reply2, dd,mWordList2.get(index).positionSpinner));
                    //mIntentListener.onIntent(reply, index);
                    Collections.sort(mWordList2);
                    finish();
                    startActivity(getIntent());

                    //viewPager.setCurrentItem(2);
                    //txtView.setText(reply);
                    //mAdapter.onAct
                    //WordListAdapter.wordItemView.setText(reply);
                    //mRecyclerView.onActivityResult(requestCode, resultCode, data)//wordItemView.setText(reply);
                    //LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

                }
                break;
            case 3:
                if (resultCode == RESULT_OK) {
                    reply = data.getStringExtra("taskName3");
                    String priority = data.getStringExtra("priorityReturn3");
                    String time = data.getStringExtra("timeReturn3");

                    int index = data.getIntExtra("returnIndex3", 0);
                    Log.d(Tag, "reply: " + index);
                    //mWordList.remove(index);
                   SubGoals = data.getStringExtra("subGoal");
                    Log.d(Tag, "sub" + SubGoals);
                    mWordList3.set(index, new Model3(reply, Integer.parseInt(priority), time, SubGoals, mWordList3.get(index).positionSpinner));
                    //mIntentListener.onIntent(reply, index);
                    Collections.sort(mWordList3);
                    finish();
                    startActivity(getIntent());

                    //txtView.setText(reply);
                    //mAdapter.onAct
                    //WordListAdapter.wordItemView.setText(reply);
                    //mRecyclerView.onActivityResult(requestCode, resultCode, data)//wordItemView.setText(reply);
                    //LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

                }
        }
    }
    public void Restart(){
        finish();
        startActivity(getIntent());
    }

    public void openRecurringIntent(View view) {
        Intent intent = new Intent(this, RecurringNotif1.class);
        startActivity(intent);
    }

    public void openRecurringIntent2(View view) {
        Intent intent = new Intent(this, RecurringNotif2.class);
        startActivity(intent);
    }
}
