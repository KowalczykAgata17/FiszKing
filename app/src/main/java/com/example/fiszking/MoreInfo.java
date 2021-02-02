package com.example.fiszking;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MoreInfo extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.more_info_fragment, new AboutApp())
                .commit();
    }
    public void goBack(View view) {
        Log.d(LOG_TAG, "Button 'GO BACK' clicked!");
        finish();
    }

    public void showAboutApp(View view) {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.more_info_fragment, new AboutApp())
                .commit();
    }

    public void showAboutAuthor(View view) {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.more_info_fragment, new AboutAuthor())
                .commit();
    }
}