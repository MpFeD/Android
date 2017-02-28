package com.example.asdmin.MonstersGO;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;

import com.example.asdmin.MonstersGO.R;

public class MainActivity extends AppCompatActivity {

    final Context context = this;
    final int permsRequestCode = 200;
    private String  username;
    private Button connectButton;
    private EditText ed;
    public static final String PREFS_NAME = "Toty_Tran_Save_Monsters_Go";

    //permission request (position)
    private void requestPerm(){
        String[] permissionLocalisation = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET};
        //requestPermissions(permissionLocalisation,permsRequestCode); //API LVL 23 & +
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int i = 0; i < permissions.length; i++) {
            String permission = permissions[i];
            int grantResult = grantResults[i];

            if (requestCode == permsRequestCode) {
                if (!(grantResult == PackageManager.PERMISSION_GRANTED)) {
                    MainActivity.this.finish();
                }
            }
        }
    }

    //get the name from a save
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPerm();

        connectButton = (Button)findViewById(R.id.buttonConnect);
        connectButton.setOnClickListener(startListener);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        username = settings.getString("withName", "");

        ed = (EditText)findViewById(R.id.textEdit);
        ed.setText(username);
    }

    //give the name to the second activity
    private View.OnClickListener startListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == connectButton.getId()) {
                username = ed.getText().toString();
                Intent intent = new Intent(context, ActivityMap.class);
                intent.putExtra("modele_username",username);
                startActivity(intent);
            }
        }
    };
}
