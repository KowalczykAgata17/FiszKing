package com.example.fiszking;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.LinkedList;

public class MyWords extends AppCompatActivity {
    private static final String LOG_TAG = MyWords.class.getSimpleName();
    private static LinkedList<HashMap<String, String>> words = new LinkedList<>();
    DatabaseHelper myDB;
    private RecyclerView recyclerView;
    private WordsAdapter wordsAdapter;

    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_words);
        myDB = new DatabaseHelper(MyWords.this);

        constraintLayout = findViewById(R.id.constraint_layout);

        Cursor cursor = myDB.readAllData();
        getWords(cursor);
        if (words != null) {
            Log.d(LOG_TAG, words.toString());
        }

        //String noteName = getIntent().getStringExtra("EXTRA_NOTE_NAME");
        //String noteContent = getIntent().getStringExtra("EXTRA_NOTE_CONTENT");

        recyclerView = findViewById(R.id.recycler_view);

        wordsAdapter = new WordsAdapter(MyWords.this, this, words);
        recyclerView.setAdapter(wordsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        enableSwipeToDeleteAndUndo();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d(LOG_TAG, "in on activity result");
        Log.d(LOG_TAG, requestCode+"");
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Log.d(LOG_TAG, "recreate");
            recreate();
        }
    }

    public void addWords(View view) {
        Intent intent = new Intent(this, AddWord.class);
        startActivityForResult(intent, 1);
    }

    public void getWords(Cursor cursor){
        Log.d(LOG_TAG, "getting words");
        words = new LinkedList<>();
//        mockNotes(2);
        while (cursor.moveToNext()){
            HashMap<String, String> word = new HashMap<String, String>();
            word.put("Id", String.valueOf(cursor.getInt(0)));
            word.put("word", cursor.getString(1));
            word.put("meaning", cursor.getString(2));

            words.add(word);
        }
        Log.d(LOG_TAG, "getting words finished");
    }


    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                final int position = viewHolder.getAdapterPosition();
                final HashMap<String, String> item = wordsAdapter.getData().get(position);
                wordsAdapter.removeItem(position);

                Snackbar snackbar = Snackbar
                        .make(constraintLayout, "Flashcard was removed.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Log.i(LOG_TAG, "restore Item "+position);
                        wordsAdapter.restoreItem(item, position);
                        recyclerView.scrollToPosition(position);
                    }
                });
                snackbar.setActionTextColor(Color.WHITE);
                snackbar.setDuration(3000);
                snackbar.show();
            }
        };
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }

    public void goBack(View view) {
        finish();
    }

    public void learn(View view) {
        Intent intent = new Intent(this, NextFlashcardActivity.class);
        startActivity(intent);
    }

}
