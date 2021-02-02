package com.example.fiszking;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.UserDictionary;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.LinkedList;

public class WordsAdapter extends RecyclerView.Adapter<WordsAdapter.WordViewHolder>{
    private final LinkedList<HashMap<String, String>> words;
    private Activity activity;
    private Context context;


    public WordsAdapter(Activity activity, Context context, LinkedList<HashMap<String, String>> words) {
        this.activity = activity;
        this.words = words;
        this.context = context;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View mItemView = inflater.inflate(R.layout.wordlist_item, parent, false);
        return new WordViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        String mCurrent = words.get(position).get("word");
        if (mCurrent.length() > 18) {
            mCurrent = mCurrent.substring(0, 15) + "...";
        }
        holder.wordItemView.setText(mCurrent);
    }


    @Override
    public int getItemCount() {
        return words.size();
    }

    public class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView wordItemView;
        final WordsAdapter mAdapter;
        private final Context context;

        public WordViewHolder(View itemView, WordsAdapter adapter) {
            super(itemView);
            context = itemView.getContext();
            wordItemView = itemView.findViewById(R.id.word_item);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int mPosition = getLayoutPosition();
            HashMap<String, String> element = words.get(mPosition);
            Intent intent = new Intent(context, EditWord.class);
            intent.putExtra("EXTRA_ID", element.get("Id"));
            intent.putExtra("EXTRA_WORD", element.get("word"));
            intent.putExtra("EXTRA_MEANING", element.get("meaning"));
            activity.startActivityForResult(intent,1);
        }
    }

    public void removeItem(int position) {
        DatabaseHelper myDB = new DatabaseHelper(WordsAdapter.this.context);
        String elem_id = words.get(position).get("Id");
        Log.i("removeItem", words.toString());
        words.remove(position);
        myDB.deleteOneRow(elem_id);

        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    public void restoreItem(HashMap<String, String> item, int position) {
        DatabaseHelper myDB = new DatabaseHelper(WordsAdapter.this.context);
        int itemPosition = myDB.addWord(item.get("word"), item.get("meaning"));
        HashMap<String, String> restored = new HashMap<String, String>();
        restored.put("Id", itemPosition+"");
        restored.put("word", item.get("word"));
        restored.put("meaning", item.get("meaning"));

        Log.i("restoreItem", words.toString());

        words.add(position, restored);
        Log.i("restoreItem", words.toString());

        notifyItemInserted(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    public LinkedList<HashMap<String, String>> getData() {
        return words;
    }
}
