package com.example.moodymusicappfinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;






import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import  com.google.firebase.auth.FirebaseUser;

public class BaseActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{
    protected FirebaseUser mfirebaseuser;
    protected GoogleApiClient mgoogleapiclient;
    protected FirebaseAuth mfirebaseauth;
    private GoogleSignInOptions mgso;
    protected void onCreate(Bundle savedIntanceState){
        super.onCreate(savedIntanceState);

        mfirebaseauth = FirebaseAuth.getInstance();
        if(mfirebaseauth!=null){
            mfirebaseuser = FirebaseAuth.getInstance().getCurrentUser();

        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken("353573543108-dalpa153srihjj90lfvo589o6vja1har.apps.googleusercontent.com").requestEmail().build();

        mgoogleapiclient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    protected void signOut(){
        mfirebaseauth.signOut();

        Auth.GoogleSignInApi.signOut(mgoogleapiclient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
               startActivity(new Intent(BaseActivity.this,MainActivity.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
