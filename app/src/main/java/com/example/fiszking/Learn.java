package com.example.fiszking;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedList;

public class Learn extends NextFlashcardActivity{
    private static LinkedList<HashMap<String, String>> random_word = new LinkedList<>();
    String Id;
    String word;
    String meaning;
    TextView wordOn;
    TextView meaningOn;

    DatabaseHelper myDB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_learn);


        /*if (notes != null) {
            Log.d(LOG_TAG, notes.toString());
        }*/



        //Next();


    }

    /*@Override
    public void Next() {
        wordOn = (TextView) findViewById(R.id.word);
        meaningOn = (TextView) findViewById(R.id.meaning);



        myDB = new DatabaseHelper(Learn.this);
        Cursor cursor = myDB.readRandomRow();





    }*/

    public void getWord(Cursor cursor){


        /*if (cursor.moveToFirst() ){
            wordOn.setText(cursor.getColumnIndex("flashcardName"));
            meaningOn.setText(cursor.getColumnIndex("flashcardContend"));
        } else {
            wordOn.setText("Ooops no data extracted");
            meaningOn.setText("Ooops no data extracted");*/
    }


        //Log.d(LOG_TAG, "getting notes finished");
}
