package com.example.moodymusicappfinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.lang.UScript;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.example.moodymusicappfinal.utils.UserSession;

public class LauncherScreen  extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_launch);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = PreferenceManager
                        .getDefaultSharedPreferences(LauncherScreen.this);
                String name = sharedPreferences.getString("useremail", "");

                if(name==""){
                    startActivity(new Intent(LauncherScreen.this,MainActivity.class));
                }else{
                    startActivity(new Intent(LauncherScreen.this,NavDrawerActivity.class));
                }
            }
        }, 2000);
    }
}
