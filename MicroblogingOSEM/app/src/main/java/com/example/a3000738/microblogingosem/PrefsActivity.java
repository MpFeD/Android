package com.example.a3000738.microblogingosem;


import android.os.Bundle;
import android.preference.PreferenceActivity;


/**
 * Created by 3000738 on 12/01/2017.
 */

public class PrefsActivity extends PreferenceActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Check if the activity was created before
        if(savedInstanceState == null){
            PrefsFragment fragment = new PrefsFragment();
            getFragmentManager().beginTransaction().add(android.R.id.content, fragment, fragment.getClass().getSimpleName()).commit();
        }

    }

}
