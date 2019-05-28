package com.example.moodymusicappfinal;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;




public class Player extends AppCompatActivity {

    MediaMetadataRetriever mmr;
    TextView songtitle;
    ImageView coverart;
    Button playbtn;
    Button stopbtn;
    Button nextbtn;
    Button backbtn;
    String filename,next,prev;

    SeekBar seekBar;
    Runnable runnable;
    Handler handler;
    MediaPlayer mediaPlayer;

int current;
ArrayList arrayList;
String name;
String path;
int state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        songtitle = (TextView)findViewById(R.id.songtitle);
        playbtn = (Button)findViewById(R.id.playbtn);
        nextbtn = (Button)findViewById(R.id.nextbtn);
        backbtn = (Button)findViewById(R.id.backbtn);
        coverart = (ImageView)findViewById(R.id.coverart);

        seekBar = (SeekBar)findViewById(R.id.seekBar);
        final Handler updateHandler = new Handler();




        state = 1;

        mmr = new MediaMetadataRetriever();

        current = getIntent().getIntExtra("i",0);
        play(current);





    }
     public void play(int i){
        if (mediaPlayer!=null){
            mediaPlayer.release();
        }
         mediaPlayer = new MediaPlayer();

         arrayList = getIntent().getStringArrayListExtra("songlist");
         name = arrayList.get(i).toString();
         path = getIntent().getStringExtra("path");

         filename = path+name;
         songtitle.setText(name);
         try {

             AssetFileDescriptor afd = getAssets().openFd(filename);
            try {
                mmr.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                byte[] data = mmr.getEmbeddedPicture();
                if (data != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    coverart.setImageBitmap(bitmap); //associated cover art in bitmap
                } else {
                    coverart.setImageResource(R.mipmap.fallback_cover); //any default cover resourse
                }
            } catch (Exception e){
            }

             mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
             mediaPlayer.prepare();

             mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                 @Override
                 public void onPrepared(MediaPlayer mp) {

                     seekBar.setMax(mediaPlayer.getDuration());
                     playCycle();
                     mediaPlayer.start();

                     final Handler updateHandler = new Handler();
                     Runnable timerRunnable = new Runnable() {

                         public void run() {
                             // Get mediaplayer time and set the value

                             // This will trigger itself every one second.
                             updateHandler.postDelayed(this, 1000);
                         }
                     };

                 }
             });


             seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                 @Override
                 public void onProgressChanged(SeekBar seekBar, int progress, boolean input) {
                     if (input){
                         mediaPlayer.seekTo(progress);
                         final Handler updateHandler = new Handler();
                         Runnable timerRunnable = new Runnable() {

                             public void run() {
                                 // Get mediaplayer time and set the value

                                 // This will trigger itself every one second.
                                 updateHandler.postDelayed(this, 1000);
                             }
                         };



                         updateHandler.postDelayed(timerRunnable, 1000);
                     }

                 }

                 @Override
                 public void onStartTrackingTouch(SeekBar seekBar) {

                 }

                 @Override
                 public void onStopTrackingTouch(SeekBar seekBar) {

                 }
             });


         } catch (IOException e) {
             e.printStackTrace();
         }
     }



    public void playCycle(){
        seekBar.setProgress(mediaPlayer.getCurrentPosition());
        if (mediaPlayer.isPlaying()){
            runnable = new Runnable() {
                @Override
                public void run() {
                playCycle();
                }
            };
            final Handler updateHandler = new Handler();
            Runnable timerRunnable = new Runnable() {

                public void run() {
                    // Get mediaplayer time and set the value

                    // This will trigger itself every one second.
                    updateHandler.postDelayed(this, 1000);
                }
            };
        }
    }
    @Override
    public void onBackPressed() {
        mediaPlayer.release();
        super.onBackPressed();
        if(path=="sad/"){
            startActivity(new Intent(Player.this,Sad.class));
        }else if(path=="happy/"){
            startActivity(new Intent(Player.this,Happy.class));
        }else if(path=="neutral/"){
            startActivity(new Intent(Player.this,Neutral.class));
        };





    }
    public void playbtn(View view){
        if (state==1) {
            mediaPlayer.start();
            playbtn.setBackgroundDrawable(getResources().getDrawable(R.mipmap.pausebtn));
            state = 0;
        }
        else {
            mediaPlayer.pause();
            playbtn.setBackgroundDrawable(getResources().getDrawable(R.mipmap.playbtn));
            state = 1;
        }
    }

    public void nextbtn(View view){
     current = current+1;
     play(current);

    }

    public void backbtn(View view){
        current = current-1;
        play(current);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
//        handler.removeCallbacks(runnable);
    }



}

