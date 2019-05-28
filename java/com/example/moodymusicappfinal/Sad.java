package com.example.moodymusicappfinal;

import android.content.Intent;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

public class Sad extends AppCompatActivity {
    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList arrayList;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sad);




        listView = findViewById(R.id.slist);
        arrayList = new ArrayList<String>();

        final AssetManager assetManager = getAssets();

        String[] list = new String[0];
        try {
            list = assetManager.list("sad");
            for (int i = 0; i < list.length; i++) {
                arrayList.add(list[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {


                Intent intent = new Intent(Sad.this,Player.class);
                intent.putExtra("i",i);
                intent.putExtra("path","sad/");
                intent.putStringArrayListExtra("songlist",arrayList);
                startActivity(intent);

            }
        });

    }
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Sad.this,NavDrawerActivity.class));
    }
}
