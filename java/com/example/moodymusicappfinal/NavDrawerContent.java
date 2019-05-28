package com.example.moodymusicappfinal;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.IOException;
import java.util.ArrayList;

public class NavDrawerContent extends AppCompatActivity {
    ImageView coverArt1, coverArt2, coverArt3, coverArt4, coverArt5, coverArt6, coverArt7, coverArt8;

    MediaMetadataRetriever mmr;
    private ArrayList arrayList;
    private ArrayList arrayList1;
    String name[];
    String path;
    int state;
    String filename[];
    private ImageView[] coverArt;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_nav_drawer);


        coverArt1 = (ImageView) findViewById(R.id.imageView4);
        coverArt2 = (ImageView) findViewById(R.id.imageView5);
        coverArt3 = (ImageView) findViewById(R.id.imageView6);
        coverArt4 = (ImageView) findViewById(R.id.imageView7);
        coverArt5 = (ImageView) findViewById(R.id.imageView8);
        coverArt6 = (ImageView) findViewById(R.id.imageView9);

        AssetManager assetManager = getAssets();
        String[] list = new String[0];
        try {
            list = assetManager.list("happy");
            for (int i = 0; i < 6; i++) {
                arrayList1.add(list[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 6; i++) {

            name[i] = arrayList1.get(i).toString();
            path = getIntent().getStringExtra("happy/");
            filename[i] = path + name[i];
            //  songtitle.setText(name);
            try {

                AssetFileDescriptor afd = getAssets().openFd(filename[i]);
                try {
                    mmr.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                    byte[] data = mmr.getEmbeddedPicture();
                    if (data != null) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                        coverArt[i+1].setImageBitmap(bitmap); //associated cover art in bitmap
                    } else {
                        coverArt[i+1].setImageResource(R.mipmap.fallback_cover); //any default cover resourse
                    }
                } catch (Exception e) {
                }

            }catch (Exception e){

            }

        }
    }
}
