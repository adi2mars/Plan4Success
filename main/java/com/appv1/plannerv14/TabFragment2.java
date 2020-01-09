package com.appv1.Plan4Success;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedList;

import static android.content.Context.MODE_PRIVATE;
import static com.appv1.Plan4Success.TabFragment1.mPreferences;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment2 extends Fragment {


    public static LinkedList<Model2> mWordList2 = new LinkedList<Model2>();
    private RecyclerView mRecyclerView;
    public WordListAdapter2 mAdapter;
    public WordListAdapter2.WordViewHolder mHolder;
    //public static SharedPreferences mPreferences;
    private String sharedPrefFile =    "com.example.android.Plan4Success";


    //public TextView wordItemView;
    public TextView prioritiesView;
    public TextView TimeManageView;
    public ImageView Remove;
    private static final String Tag = "MyActivity";
    public static final int TEXT_REQUEST = 1;
    //public static String reply = "";
    TextView txtView;
    TextView txtView2;
    String reply;
    TabFragment1.OnIntentReceived mIntentListener;
    Calendar calendar;
    DateFormat dateFormat;
    private AdView mAdView;

    public TabFragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tab_fragment2, container, false);
        MobileAds.initialize(getContext());
        mAdView = view.findViewById(R.id.adView2);
        //mAdView.setAdSize(AdSize.BANNER);
        //mAdView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mPreferences = getContext().getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        //Log.d(Tag, "saveda " + mPreferences.getInt("Size2", 0));
        if(mWordList2.size()==0){
            for(int i = 0; i < mPreferences.getInt("SizeB", 0); i++) {
                mWordList2.add(new Model2(
                        mPreferences.getString("Assignment"+i, "Enter Assignment"),
                        mPreferences.getString("Date"+i, "0"),
                        mPreferences.getInt("PositionSpin" + i, 0)));
                Log.d(Tag, "saveda"+ mWordList2.get(i).Assignment);
            }
        }
        //txtView = findViewById(R.id.word);
        //txtView2 = findViewById(R.id.priorities);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int wordListSize = mWordList2.size();
                // Add a new word to the wordList.
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                String date = (month+1) + "/" + day + "/" + year;
                mWordList2.addLast(new Model2("Enter in Task",date,0));
                // Notify the adapter, that the data has changed.
                mRecyclerView.getAdapter().notifyItemInserted(wordListSize);
                // Scroll to the bottom.
                mRecyclerView.smoothScrollToPosition(wordListSize);
                //Collections.sort(mWordList2);
                //finish();
                //startActivity(getIntent());
                //overridePendingTransition(0, 0);

            }
        });
        // Put initial data into the word list.
        /*for (int i = 0; i < 5; i++) {
            int wordListSize = mWordList.size();
            mWordList.addLast(new Model("Enter in Task" + (wordListSize+1),wordListSize+1, 0));
        }*/
        // Get a handle to the RecyclerView.
        mRecyclerView = view.findViewById(R.id.recyclerview2);
// Create an adapter and supply the data to be displayed.
        mAdapter = new WordListAdapter2(getContext(), mWordList2);
// Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        mAdapter.setOnItemClickListener(new WordListAdapter.OnItemClickListener() {
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
                mWordList2.remove(position);
                mAdapter.notifyItemRemoved(position);
                /*mWordList2.remove(position);
                mRecyclerView.removeViewAt(position);
                //Log.d(Tag, "Adi123");
                mAdapter.notifyItemRemoved(position);
                mAdapter.notifyItemRangeChanged(position, mWordList2.size());
                mAdapter.notifyDataSetChanged();*/


                //finish();
                //startActivity(getIntent());

            }
        });
// Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //wordItemView = (TextView) findViewById(R.id.word);
        //Log.d(Tag,"current123" + wordItemView.getText().toString());
        // prioritiesView = (TextView) findViewById(R.id.priorities);
        //TimeManageView = (TextView) findViewById(R.id.timeManage);
        return view;



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }





}


