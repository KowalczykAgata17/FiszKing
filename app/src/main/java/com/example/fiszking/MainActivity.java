package com.example.fiszking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.fiszking.MoreInfo;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Start(View view){
        Log.d(LOG_TAG, "Button 'Zaczynajmy!' clicked!");
        Intent intent = new Intent(this, MyWords.class);
        startActivity(intent);
    }

    public void More( View view){
        Log.d(LOG_TAG, "Button 'Więcej infoooooo' clicked!");
        Intent intent = new Intent(this, MoreInfo.class);
        startActivity(intent);
    }
}