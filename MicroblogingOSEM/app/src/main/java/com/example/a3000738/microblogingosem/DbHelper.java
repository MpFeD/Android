package com.example.a3000738.microblogingosem;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.example.a3000738.microblogingosem.DbStructure.CREATED_AT;

/**
 * Created by 3000738 on 18/01/2017.
 */

public class DbHelper extends SQLiteOpenHelper{

    private static final String TAG = "DbHelper";
    private static final String CREATE_DB = "CREATE TABLE "  + DbStructure.TABLE
            + " (" +
            DbStructure.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CREATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
            DbStructure.USER + " TEXT NOT NULL, " +
            DbStructure.MESSAGE + " TEXT NOT NULL" +
            ");";

    public DbHelper(Context context){
        super(context, DbStructure.DB_NAME, null, DbStructure.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, CREATE_DB);
        db.execSQL(CREATE_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DbStructure.TABLE);
        onCreate(db);
    }
}
