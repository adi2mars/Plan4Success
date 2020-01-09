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
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.LinkedList;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment1 extends Fragment {
    public interface OnIntentReceived {
        void onIntent(String reply, int position);
    }


    public static LinkedList<Model> mWordList = new LinkedList<Model>();
    private RecyclerView mRecyclerView;
    public WordListAdapter mAdapter;
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
    OnIntentReceived mIntentListener;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    public int counter =0;


    public TabFragment1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tab_fragment1, container, false);
        MobileAds.initialize(getContext());
        mAdView = view.findViewById(R.id.adView);
        //mAdView.setAdSize(AdSize.BANNER);
        //mAdView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-1820283267507599/4639301595");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        mPreferences = getContext().getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        if(mWordList.size()==0){
            for(int i = 0; i < mPreferences.getInt("Size", 0); i++) {
                mWordList.add(new Model(
                        mPreferences.getString("Task"+i, "Enter Task"),
                        mPreferences.getInt("Priority"+i, 0),
                        mPreferences.getInt("Ti"+i, 0)));
                Log.d(Tag, "saved22"+ mPreferences.getString("Task"+i, "ll"));
            }
        }

        //txtView = findViewById(R.id.word);
        //txtView2 = findViewById(R.id.priorities);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(counter==0){
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                        counter++;
                    } else {
                        Log.d("TAG", "The interstitial wasn't loaded yet.");
                    }
                }
                int wordListSize = mWordList.size();
                // Add a new word to the wordList.
                int a =0;
                if(wordListSize==0){
                    a = 0;
                }else {
                    a = mWordList.get(wordListSize - 1).priority;
                }
                mWordList.addLast(new Model("Enter in Task",a+1, 0));
                // Notify the adapter, that the data has changed.
                mAdapter.notifyItemInserted(wordListSize);
                //mRecyclerView.getAdapter().notifyItemInserted(wordListSize);
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
        mRecyclerView = view.findViewById(R.id.recyclerview);
// Create an adapter and supply the data to be displayed.
        mAdapter = new WordListAdapter(getContext(), mWordList);
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
                mWordList.remove(position);
                mAdapter.notifyItemRemoved(position);

                //mAdapter.notifyDataSetChanged();
                /*int s = mPreferences.getInt("Size",0);
                if(s!=0){

                }*/
                /*View v = mRecyclerView.getChildAt(position);
                Log.d(Tag, "Removing" + position + (v==null));
                Log.d(Tag, "Removingi" + position + mWordList.get(position).priority);

                if(position<8) {
                    mRecyclerView.removeViewAt(position);
                }else{
                    int a =position-7;
                    mRecyclerView.removeViewAt(position-a);
                }
                mWordList.remove(position);
                //Log.d(Tag, "Adi123");
                mAdapter.notifyItemRemoved(position);
                mAdapter.notifyItemRangeChanged(position, mWordList.size());
                mAdapter.notifyDataSetChanged();


                //finish();
                //startActivity(getIntent());
                */

            }
        });
// Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //wordItemView = (TextView) findViewById(R.id.word);
        //Log.d(Tag,"current123" + wordItemView.getText().toString());
        // prioritiesView = (TextView) findViewById(R.id.priorities);
        //TimeManageView = (TextView) findViewById(R.id.timeManage);
        mIntentListener = mAdapter;
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


