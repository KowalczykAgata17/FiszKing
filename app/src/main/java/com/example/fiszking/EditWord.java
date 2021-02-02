package com.example.fiszking;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditWord extends AppCompatActivity {
    String Id;
    String word;
    String meaning;
    private EditText wordEditText;
    private EditText meaningEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_word);

        wordEditText = (EditText) findViewById(R.id.word);
        meaningEditText = (EditText) findViewById(R.id.meaning);

        Id = getIntent().getStringExtra("EXTRA_ID");
        word = getIntent().getStringExtra("EXTRA_WORD");
        meaning = getIntent().getStringExtra("EXTRA_MEANING");

        wordEditText.setText(word);
        meaningEditText.setText(meaning);
    }

    public void saveWord(View view) {
        DatabaseHelper myDB = new DatabaseHelper(EditWord.this);
        word = wordEditText.getText().toString().trim();
        meaning = meaningEditText.getText().toString().trim();
        myDB.updateData(Id, word, meaning);
//        String editedNoteName = noteNameEditText.getText().toString();
//        String editedNoteContent = noteContentEditText.getText().toString();
        finish();
    }

    public void goBack(View view) {
        finish();
    }
}
