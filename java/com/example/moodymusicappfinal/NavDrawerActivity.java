package com.example.moodymusicappfinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.example.moodymusicappfinal.models.Users;
import com.example.moodymusicappfinal.EmotionDetector;
//import com.example.moodymusicappfinal.utils.UserSession;
import com.example.moodymusicappfinal.utils.constants;
import com.google.firebase.FirebaseApp;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;


public class NavDrawerActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
 //  ImageView coverArt1,coverArt2,coverArt3,coverArt4,coverArt5,coverArt6;

    String name[];
    String path;
    int state;
    String filename[];
    ListView listView ;
    ArrayAdapter<String> adapter;
    ArrayList arrayList;
    MediaPlayer mediaPlayer;


    boolean doubleBackToExitPressedOnce = false;

    private ArrayList arrayList1;
    MediaMetadataRetriever mmr;
    private NavDrawerActivity NavDrawer;
    TextView txtv;
    AssetFileDescriptor afd;
    HorizontalScrollView scrollView;
     ListView listView1;
    private ArrayAdapter adapter1;
    private ArrayList<String> arrayList11;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseApp.initializeApp(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        NavDrawer = new NavDrawerActivity();


        ImageView coverArt[] = new ImageView[6];


           coverArt[0] = (ImageView)findViewById(R.id.imageView4);
        coverArt[1] = (ImageView)findViewById(R.id.imageView5);
        coverArt[2] = (ImageView)findViewById(R.id.imageView6);
        coverArt[3] = (ImageView)findViewById(R.id.imageView7);
        coverArt[4] = (ImageView)findViewById(R.id.imageView8);
        coverArt[5] = (ImageView)findViewById(R.id.imageView9);
        scrollView = new HorizontalScrollView(this);


        setSupportActionBar(toolbar);

        FirebaseApp.initializeApp(this);




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View navheaderview = navigationView.getHeaderView(0);
        final ImageView displayimage = (ImageView)navheaderview.findViewById(R.id.imageView_display);
        final TextView displayname = (TextView) navheaderview.findViewById(R.id.textview_uname);

        final TextView displayemail = (TextView)navheaderview.findViewById(R.id.textView_email);
        FirebaseDatabase.getInstance().getReference(constants.USER_KEY).child(mfirebaseuser.getEmail().replace(".",","))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue() !=null){
                            Users users = dataSnapshot.getValue(Users.class);
                            String useremailadd = users.getEmail();
                            SharedPreferences sharedPreferences = PreferenceManager
                                    .getDefaultSharedPreferences(NavDrawerActivity.this);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("useremail", useremailadd);
                            editor.apply();

                            Glide.with(NavDrawerActivity.this).load(users.getPhotourl()).into(displayimage);

                            displayemail.setText(useremailadd);
                            displayname.setText(users.getUsers());


                        }





                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
        arrayList1 = new ArrayList<String>();
        name = new String[6];
         filename = new String[6];
        mmr = new MediaMetadataRetriever();


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
            path = "happy/";
            filename[i] = path + name[i];
            //  songtitle.setText(name);
            try {

                AssetFileDescriptor afd = getAssets().openFd(filename[i]);
                try {
                    mmr.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                    byte[] data = mmr.getEmbeddedPicture();
                    if (data != null) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                        coverArt[i].setImageBitmap(bitmap); //associated cover art in bitmap
                    } else {
                        coverArt[i].setImageResource(R.mipmap.fallback_cover); //any default cover resourse
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }catch (Exception e){
               e.printStackTrace();
            }

        }



        listView1 =findViewById(R.id.nlist);
        arrayList11 = new ArrayList<String>();

        AssetManager assetManager1 = getAssets();

        String[] list1 = new String[0];
        try {
            list1 = assetManager1.list("neutral");
            for(int i=0;i<list1.length;i++){
                arrayList11.add(list1[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        adapter1 = new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList11);
        listView1.setAdapter(adapter1);

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {

                Intent intent = new Intent(NavDrawerActivity.this,Player.class);
                intent.putExtra("i",i);
                intent.putExtra("path","neutral/");
                intent.putStringArrayListExtra("songlist",arrayList11);
                startActivity(intent);

            }
        });
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(NavDrawerActivity.this, "Double tap to exit !!", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            super.onBackPressed();
            return;
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(NavDrawerActivity.this,Setting.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        android.app.FragmentManager fragmentManager = getFragmentManager();
        if (id == R.id.nav_camera) {
        startActivity(new Intent(NavDrawerActivity.this,EmotionDetector.class));

            // Handle the camera action
        } else if (id == R.id.nav_camera) {
            //startActivity(new Intent(NavDrawerActivity.this,RecognizeEmotion.class));
          //  startActivity(new Intent(NavDrawerActivity.this,))
        } else if (id == R.id.nav_playlist) {
           startActivity(new Intent(NavDrawerActivity.this,Playlist.class));

        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(NavDrawerActivity.this,Setting.class));

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_logout) {
            startActivity(new Intent(NavDrawerActivity.this,MainActivity.class));
        }





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
     public void send1(View view){


                Intent intent = new Intent(NavDrawerActivity.this,Player.class);
                intent.putExtra("i",0);
                intent.putExtra("path","happy/");
                intent.putStringArrayListExtra("songlist",arrayList1);
                startActivity(intent);

     }


    public void send2(View view){


        Intent intent = new Intent(NavDrawerActivity.this,Player.class);
        intent.putExtra("i",1);
        intent.putExtra("path","happy/");
        intent.putStringArrayListExtra("songlist",arrayList1);
        startActivity(intent);

    }

    public void send3(View view){


        Intent intent = new Intent(NavDrawerActivity.this,Player.class);
        intent.putExtra("i", 2);
        intent.putExtra("path","happy/");
        intent.putStringArrayListExtra("songlist",arrayList1);
        startActivity(intent);

    }
    public void send4(View view){


        Intent intent = new Intent(NavDrawerActivity.this,Player.class);
        intent.putExtra("i",3);
        intent.putExtra("path","happy/");
        intent.putStringArrayListExtra("songlist",arrayList1);
        startActivity(intent);

    }
    public void send5(View view){


        Intent intent = new Intent(NavDrawerActivity.this,Player.class);
        intent.putExtra("i",4);
        intent.putExtra("path","happy/");
        intent.putStringArrayListExtra("songlist",arrayList1);
        startActivity(intent);

    }
    public void send6(View view){


        Intent intent = new Intent(NavDrawerActivity.this,Player.class);
        intent.putExtra("i",5);
        intent.putExtra("path","happy/");
        intent.putStringArrayListExtra("songlist",arrayList1);
        startActivity(intent);

    }

    }



