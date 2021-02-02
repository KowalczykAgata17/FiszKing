package com.example.fiszking;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NextFlashcardActivity extends AppCompatActivity implements SensorEventListener {
    private static final String LOG_TAG = NextFlashcardActivity.class.getSimpleName();

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float[] mGravity;
    private Sensor magnetometer;
    private float[] mGeomagnetic;
    String Id;
    String word;
    String meaning;
    TextView wordOn;
    TextView meaningOn;

    DatabaseHelper myDB;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer == null) {
            Log.d(LOG_TAG, "accelerometer is null");
        }
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if (magnetometer == null) {
            Log.d(LOG_TAG, "magnetometer is null");
        }



        //Id = getIntent().getStringExtra("EXTRA_ID");
       // word = getIntent().getStringExtra("EXTRA_WORD");
       // meaning = getIntent().getStringExtra("EXTRA_MEANING");

        //wordOn.setText(word);
        //meaningOn.setText(meaning);
    }

    @Override
    protected void onResume() {
        super.onResume();
        start();
    }

    public void start() {
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stop();
    }

    public void stop() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.values == null) {
            Log.w(LOG_TAG, "event.values is null");
            return;
        }
        int sensorType = event.sensor.getType();
        switch (sensorType) {
            case Sensor.TYPE_ACCELEROMETER:
                mGravity = event.values;
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                mGeomagnetic = event.values;
                break;
            default:
                Log.w(LOG_TAG, "Unknown sensor type " + sensorType);
                return;
        }
        if (mGravity == null) {
            Log.w(LOG_TAG, "mGravity is null");
            return;
        }
        if (mGeomagnetic == null) {
            Log.w(LOG_TAG, "mGeomagnetic is null");
            return;
        }
        float rot[] = new float[9];
        if (!SensorManager.getRotationMatrix(rot, null, mGravity, mGeomagnetic)) {
            Log.w(LOG_TAG, "getRotationMatrix() failed");
            return;
        }

        float[] orientation = new float[9];
        SensorManager.getOrientation(rot, orientation);
        float roll = orientation[2];
        int rollDeg = (int) Math.round(Math.toDegrees(roll));
        if (rollDeg < -50 && rollDeg > -130) {
            Log.i(LOG_TAG, "------------------LEFT---------------");
            stop();
            goBack();
        }
        if (rollDeg > 50 && rollDeg < 130) {
            Log.i(LOG_TAG, "------------------RIGHT---------------");
            stop();
            Next();
        }

        float roll2 = orientation[1];
        int rollDeg2 = (int) Math.round(Math.toDegrees(roll2));
        /*if (rollDeg2 < -80 && rollDeg2 > -130) {
            Log.i(LOG_TAG, "------------------Forward---------------");
            stop();
            Next();
        }*/
        if (rollDeg2 > 0 && rollDeg2 < 130) {
            Log.i(LOG_TAG, "------------------backward---------------");
            stop();
            flip();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void Next(View view) {
        Next();
    }

    public void Next() {
        wordOn = (TextView) findViewById(R.id.wordOn);
        //meaningOn = (TextView) findViewById(R.id.meaningOn);

        myDB = new DatabaseHelper(NextFlashcardActivity.this);
        Cursor cursor = myDB.readRandomRow();

        cursor.moveToFirst();
        word=cursor.getString(1);
        wordOn.setText(word);
        //meaning=cursor.getString(2);
        //meaningOn.setText(meaning);

        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            Log.e("SplashScreen", e.getMessage());
        }

        onPause();

        onResume();

    }

    public void flip(View view) {
        flip();
    }

    public void flip() {

        wordOn = (TextView) findViewById(R.id.wordOn);
        //meaningOn = (TextView) findViewById(R.id.meaningOn);

        //Id = getIntent().getStringExtra("EXTRA_ID");
        //word = getIntent().getStringExtra("EXTRA_WORD");
        //meaning = getIntent().getStringExtra("EXTRA_MEANING");

        myDB = new DatabaseHelper(NextFlashcardActivity.this);
        word = wordOn.getText().toString();
        //Cursor cursor = myDB.getCurrent(word);
        Cursor cursor = myDB.getCurrent(word);

        cursor.moveToFirst();
        meaning=cursor.getString(2);
        wordOn.setText(meaning);
        //meaning=cursor.getString(2);
        //meaningOn.setText(meaning);

        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            Log.e("SplashScreen", e.getMessage());
        }

        onPause();

        onResume();
    }



    public void goBack(View view) {
        goBack();
    }

    public void goBack() {
        finish();
    }
}

