package com.example.a3000738.microblogingosem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    public TimelineFragment tf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    // Called every time user clicks on an action
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(this, RefreshStatusService.class);
        switch (item.getItemId()) {
            case R.id.itemStatus:
                startActivity(new Intent(this, StatusActivity.class));
                return true;
            case R.id.itemPrefs:
                startActivity(new Intent(this, PrefsActivity.class));
                return true;
            case R.id.itemServiceOn:
                startService(intent);
                Toast.makeText(this, "Service on", Toast.LENGTH_LONG).show();
                tf = new TimelineFragment();
                getFragmentManager().beginTransaction().replace(R.id.container, tf).commit();
                return true;
            case R.id.itemServiceOff:
                stopService(intent);
                Toast.makeText(this, "Service off", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
