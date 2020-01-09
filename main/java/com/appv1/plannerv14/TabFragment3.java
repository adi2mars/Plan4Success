package com.appv1.Plan4Success;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import java.util.LinkedList;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment3 extends Fragment {

    public static LinkedList<Model3> mWordList3 = new LinkedList<Model3>();
    public static LinkedList<String> mWordList4 = new LinkedList<String>();
    private RecyclerView mRecyclerView2;
    public WordListAdapter4 mAdapter2;
    private RecyclerView mRecyclerView;
    public WordListAdapter3 mAdapter;
    public WordListAdapter.WordViewHolder mHolder;
    public static SharedPreferences mPreferences;
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
    //OnIntentReceived mIntentListener;
    private AdView mAdview;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tab_fragment3, container, false);
        //mPreferences = getContext().getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        //txtView = findViewById(R.id.word);
        //txtView2 = findViewById(R.id.priorities);
        MobileAds.initialize(getContext());
        mAdview = view.findViewById(R.id.adView3);
        //mAdView.setAdSize(AdSize.BANNER);
        //mAdView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdview.loadAd(adRequest);
        mPreferences = getContext().getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        if(mWordList3.size()==0){
            for(int i = 0; i < mPreferences.getInt("SizeC", 0); i++) {
                mWordList3.add(new Model3(
                        mPreferences.getString("Goal"+i, "Enter Goal"),
                        mPreferences.getInt("Priority3"+i,1),
                        mPreferences.getString("Approx"+i, "0"),
                        mPreferences.getString("SubG"+i, "Enter SubGoal"),
                        mPreferences.getInt("PositionSpin2"+i, 0)));
                Log.d(Tag, "savedad"+ mWordList3.get(i).positionSpinner);
            }
        }
        FloatingActionButton fab = view.findViewById(R.id.fab3);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int wordListSize = mWordList3.size();
                // Add a new word to the wordList.
                int a =0;
                if(wordListSize==0){
                    a = 0;
                }else {
                    a = mWordList3.get(wordListSize - 1).priority3;
                }
                Calendar c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);
                String date = (month+1) + "/" + day + "/" + year;
                mWordList3.addLast(new Model3("Enter in Goal",a+1, date, "Enter in Sub-Goals",0));
                // Notify the adapter, that the data has changed.
                mRecyclerView.getAdapter().notifyItemInserted(wordListSize);
                // Scroll to the bottom.
                mRecyclerView.smoothScrollToPosition(wordListSize);
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
        mRecyclerView = view.findViewById(R.id.recyclerview3);
        //mRecyclerView2 = view.findViewById(R.id.recyclerView4);

// Create an adapter and supply the data to be displayed.
        mAdapter = new WordListAdapter3(getContext(), mWordList3);
        //mAdapter2 = new WordListAdapter4(getContext(), mWordList4);

// Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
       //mRecyclerView2.setAdapter(mAdapter2);
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

                mWordList3.remove(position);
                mAdapter.notifyItemRemoved(position);

                //finish();
                //startActivity(getIntent());

            }
        });
// Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //mRecyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));

        //wordItemView = (TextView) findViewById(R.id.word);
        //Log.d(Tag,"current123" + wordItemView.getText().toString());
        // prioritiesView = (TextView) findViewById(R.id.priorities);
        //TimeManageView = (TextView) findViewById(R.id.timeManage);
        //mIntentListener = mAdapter;
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


