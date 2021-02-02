package com.example.fiszking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String LOG_TAG = DatabaseHelper.class.getSimpleName();

    // Table Name
    public static final String TABLE_NAME = "FLASHCARDS";

    // Table columns
    public static final String _ID = "_id";
    public static final String WORD = "flashcardName";
    public static final String MEANING = "flashcardContend";

    // Database Information
    static final String DB_NAME = "FLASHCARDS.db";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + WORD + " TEXT NOT NULL, " + MEANING + " TEXT NOT NULL);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    int addWord(String word, String meaning){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(WORD, word);
        cv.put(MEANING, meaning);
        long result = db.insert(TABLE_NAME,null, cv);
        if(result == -1){
            Log.i(LOG_TAG, "Failed to add word");
        }else {
            Log.i(LOG_TAG, "Word added succesfully!" + word + " " + result);
        }
        return (int) result;
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    Cursor readRandomRow(){
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY RANDOM() LIMIT 1 ";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    Cursor getCurrent(String word){
        //String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + WORD + " = " + word;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("SELECT * FROM  FLASHCARDS  where flashcardName='"+word+"'" , null);
        }
        return cursor;
    }

    void updateData(String row_id, String word, String meaning){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(WORD, word);
        cv.put(MEANING, meaning);
        Log.i(LOG_TAG, row_id+"");
        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(result != 1){
            Log.i(LOG_TAG, "Failed to update word, passwd id was "+ row_id);
        }else {
            Log.i(LOG_TAG, "Word updated succesfully! "+ result);
        }
    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == 0){
            Log.i(LOG_TAG, "Failed to delete word, id was "+ row_id);
        }else {
            Log.i(LOG_TAG, "Word deleted succesfully!: "+row_id);
        }
    }

    void deleteAllData(){
        Log.i(LOG_TAG, "Deleting all data");
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
        Log.i(LOG_TAG, "Deleting all data finished");

    }
}
