package com.example.a3000738.microblogingosem;

import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;
import android.os.Bundle;

import java.util.List;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;

/**
 * Created by 3000738 on 15/01/2017.
 */

public class RefreshStatusService extends Service {
    private static final String TAG = "RefreshStatusService";
    private RefreshThread myThread;
    private SharedPreferences prefs;
    private Twitter twitter;
    private TimelineFragment timelineFragment;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        timelineFragment = new TimelineFragment();

        this.myThread = new RefreshThread(this);
        this.myThread.start();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy(){
        this.myThread.stopThread();
        super.onDestroy();
    }

    /* Retrieve statuses from friends */
    public void retrieveStatuses(){
        String username = this.prefs.getString("username", "");
        String password = this.prefs.getString("password", "");
        String apiRoot = this.prefs.getString("apiRoot", "");

        if(username.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Please update your username and password", Toast.LENGTH_LONG).show();
            return;
        }

        /* Create the instance of DbHelper and then get the writable database */
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        /* Map database table names to their respective values */
        ContentValues contentValues = new ContentValues();

        twitter = new Twitter(username, password);
        twitter.setAPIRootUrl(apiRoot);

        try{
            List<Twitter.Status> friendsStatuses = twitter.getFriendsTimeline();
            for(Twitter.Status status : friendsStatuses){
                Log.d(TAG, String.format("%s : %s", status.getUser().toString(), status.getText()));
                contentValues.put(DbStructure.ID, status.getId());
                contentValues.put(DbStructure.USER, status.getUser().toString());
                contentValues.put(DbStructure.MESSAGE, status.getText());
                contentValues.put(DbStructure.CREATED_AT, status.getCreatedAt().getTime());

                /* Avoid any conflict due to ID being unique from inserting old version */
                db.insertWithOnConflict(DbStructure.TABLE, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
            }

        }catch (TwitterException te){
            te.printStackTrace();
        }

        return;
    }

}
