package com.example.fiszking;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddWord extends AppCompatActivity {

    String word;
    String meaning;
    private EditText wordEditText;
    private EditText meaningEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        wordEditText = (EditText) findViewById(R.id.word);
        meaningEditText = (EditText) findViewById(R.id.meaning);

        wordEditText.setText("");
        meaningEditText.setText("");

    }

    public void saveWord(View view) {
        DatabaseHelper myDB = new DatabaseHelper(AddWord.this);
        word = wordEditText.getText().toString().trim();
        meaning = meaningEditText.getText().toString().trim();
        if (word.length() > 0 || meaning.length() >0) {
            myDB.addWord(word, meaning);
        }
        finish();
    }

    public void goBack(View view) {
        finish();
    }

}
