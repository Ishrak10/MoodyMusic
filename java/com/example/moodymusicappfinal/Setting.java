package com.example.moodymusicappfinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Setting extends AppCompatActivity {

    Button btb;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        btb =(Button)findViewById(R.id.logbtn);





    }

    public void logout(View view){
      startActivity(new Intent(Setting.this,MainActivity.class));
    }
}
