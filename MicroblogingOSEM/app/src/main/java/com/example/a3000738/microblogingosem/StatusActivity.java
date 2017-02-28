package com.example.a3000738.microblogingosem;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;


public class StatusActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher, SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String TAG = "StatusActivity";
    Button btnUpdate;
    TextView textViewCount;
    EditText editTextStatus;
    Twitter twitter;

    /* It's called when the activity is first created */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        btnUpdate = (Button) findViewById(R.id.buttonUpdate);
        btnUpdate.setOnClickListener(this);

        textViewCount = (TextView) findViewById(R.id.textCount);
        textViewCount.setText(Integer.toString(140));
        textViewCount.setTextColor(Color.GREEN);

        editTextStatus = (EditText) findViewById(R.id.editText);
        editTextStatus.addTextChangedListener(this);

    }

    /* It's called when the Update button is clicked */
    @Override
    public void onClick(View v) {
        String status = editTextStatus.getText().toString();
        new PostTask().execute(status);
        Log.d(TAG, "onClick debug: " + status);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        twitter = null;
    }

    /* Class PostTask is a task asynchronous in order to run the update in background */
    class PostTask extends AsyncTask<String, Integer, String>{

        /* It's called to initiate the background activity */
        @Override
        protected String doInBackground(String... params) {
            try{
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String username = prefs.getString("username", "");
                String password = prefs.getString("password", "");
                String api = prefs.getString("apiRoot", "");

                Log.d(TAG, "Thread: " + username + "|" + password);
                Log.d(TAG, "Thread: " + api);

                if(username.isEmpty() || password.isEmpty()){
                    getApplicationContext().startActivity(new Intent(getApplicationContext(), PrefsActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    return "Please update your username and password !";
                }

                twitter = new Twitter(username, password);
                twitter.setAPIRootUrl(api);
                Twitter.Status status = twitter.updateStatus(params[0]);

                return "Successfully posted : " + status.text;
            }catch (TwitterException te){
                Log.e(TAG, "onClick error: " + te.toString());
                return "Failed to post on twitter !";
            }
        }

        /* It's called when there is a status update */
        protected void onProgressUpdate(Integer... progress){
            super.onProgressUpdate(progress[0]);
        }

        /* It's called when the background activity has finished */
        protected void onPostExecute(String result){
            Toast.makeText(StatusActivity.this, result, Toast.LENGTH_LONG).show();
        }
    }

    /* Implement methods from TextWatcher */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable editStatus) {
        int cpt = 140 - editTextStatus.getText().length();
        textViewCount.setText(Integer.toString(cpt));
        textViewCount.setTextColor(Color.GREEN);
        if (cpt < 20)
            textViewCount.setTextColor(Color.YELLOW);
        if (cpt < 0)
            textViewCount.setTextColor(Color.RED);
    }

    /* It's first called when the user clicks on the menu button */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Create a MenuInflater in order to get the menu file XML
        MenuInflater inflater = getMenuInflater();
        // Instanciate the menu XML as an object Menu
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    /* It's called everytime when the user clicks on the menu item */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, RefreshStatusService.class);
        switch (item.getItemId()){
            case R.id.itemStatus:
                startActivity(new Intent(this, StatusActivity.class));
                break;
            case R.id.itemPrefs:
                startActivity(new Intent(this, PrefsActivity.class));
                break;
            case R.id.itemServiceOn:
                startService(intent);
                Toast.makeText(this, "Service on", Toast.LENGTH_LONG).show();
                break;
            case R.id.itemServiceOff:
                stopService(intent);
                Toast.makeText(this, "Service off", Toast.LENGTH_LONG).show();
            default:
                return false;
        }
        return true;
    }

    private Twitter getTwitter(){
        if(this.twitter == null)
            this.twitter = new Twitter();

        return twitter;
    }
}
