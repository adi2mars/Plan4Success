package com.appv1.Plan4Success;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.LinkedList;

public class WordListAdapter4 extends
        RecyclerView.Adapter<WordListAdapter4.WordViewHolder> {

    public static LinkedList<String> mWordList4;
    private final LayoutInflater mInflater;
    private WordListAdapter4.OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(int position);

        void onDeleteClick(int position);
    }

    class WordViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        public final TextView wordItemView;
        public final ImageButton cancelView;

        final WordListAdapter4 mAdapter;

        /**
         * Creates a new custom view holder to hold the view to display in
         * the RecyclerView.
         *
         * @param itemView The view in which to display the data.
         * @param adapter The adapter that manages the the data and views
         *                for the RecyclerView.
         */
        public WordViewHolder(View itemView, WordListAdapter4 adapter) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.SubGoal);
            cancelView = itemView.findViewById(R.id.button4);
            this.mAdapter = adapter;
            cancelView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onDeleteClick(getAdapterPosition());

                            //removeAt(position);
                            //notifyItemRemoved(position);
                            //notifyItemRangeChanged(position, mWordList.size());
                            //Log.d(Tag, "answer: " + wordItemView.getText());
                            //wordItemView.setText("Enter in Task");
                            //prioritiesView.setText(getAdapterPosition());
                            //TimeManageView.setText(0);
                        }
                    }
                }
            });
            wordItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final EditText taskEditText = new EditText(v.getContext());
                    AlertDialog dialog = new AlertDialog.Builder(v.getContext())
                            .setTitle("Add a new task")
                            .setMessage("What do you want to do next?")
                            .setView(taskEditText)
                            .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String task = String.valueOf(taskEditText.getText());
                                    //Log.d(tag, "Task to add: " + task);
                                    wordItemView.setText(task);
                                    mWordList4.set(getAdapterPosition(),task);
                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .create();
                    dialog.show();
                }
            });
        }

        @Override
        public void onClick(View view) {
            // Get the position of the item that was clicked.
            /*int mPosition = getLayoutPosition();

            // Use that to access the affected item in mWordList.
            String element = mWordList4.get(mPosition);
            // Change the word in the mWordList.

            mWordList4.set(mPosition, "Clicked! " + element);
            // Notify the adapter, that the data has changed so it can
            // update the RecyclerView to display the data.
            mAdapter.notifyDataSetChanged();*/
        }
    }

    public WordListAdapter4(Context context, LinkedList<String> wordList) {
        mInflater = LayoutInflater.from(context);
        this.mWordList4 = wordList;
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to
     * represent an item.
     *
     * This new ViewHolder should be constructed with a new View that can
     * represent the items of the given type. You can either create a new View
     * manually or inflate it from an XML layout file.
     *
     * The new ViewHolder will be used to display items of the adapter using
     * onBindViewHolder(ViewHolder, int, List). Since it will be reused to
     * display different items in the data set, it is a good idea to cache
     * references to sub views of the View to avoid unnecessary findViewById()
     * calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after
     *                 it is bound to an adapter position.
     * @param viewType The view type of the new View. @return A new ViewHolder
     *                 that holds a View of the given view type.
     */
    @Override
    public WordListAdapter4.WordViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // Inflate an item view.
        View mItemView = mInflater.inflate(
                R.layout.wordlist_item4, parent, false);
        return new WordViewHolder(mItemView, this);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * This method should update the contents of the ViewHolder.itemView to
     * reflect the item at the given position.
     *
     * @param holder   The ViewHolder which should be updated to represent
     *                 the contents of the item at the given position in the
     *                 data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(WordListAdapter4.WordViewHolder holder,
                                 int position) {
        // Retrieve the data for that position.
        String mCurrent = mWordList4.get(position);
        // Add the data to the view holder.
        holder.wordItemView.setText(mCurrent);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mWordList4.size();
    }
    public void setOnItemClickListener(WordListAdapter4.OnItemClickListener listener) {
        mListener = listener;
    }
}
