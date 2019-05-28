package com.example.moodymusicappfinal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.example.moodymusicappfinal.utils.UserSession;
import com.example.moodymusicappfinal.utils.constants;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.moodymusicappfinal.models.Users;


public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 9001;

    private FirebaseAuth.AuthStateListener mauthlistner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseApp.initializeApp(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.sign_in_button).setOnClickListener(this);
        

        FirebaseApp.initializeApp(this);

        mauthlistner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mfirebaseuser = firebaseAuth.getCurrentUser();
                if (mfirebaseuser != null) {
                    if (mfirebaseuser != null) {
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "onAuthStateChanged:signed_in" + mfirebaseuser.getDisplayName());
                    } else {
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "onAuthStateChanged:signed_out");
                    }
                }
            }
        };
    }


    @Override
    protected void onStart() {
        super.onStart();
        mfirebaseauth.addAuthStateListener(mauthlistner);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mauthlistner != null) {
            mfirebaseauth.removeAuthStateListener(mauthlistner);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
        }

    }

    private void signIn() {
        Intent signinintent = Auth.GoogleSignInApi.getSignInIntent(mgoogleapiclient);
        startActivityForResult(signinintent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == RC_SIGN_IN) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                if (result.isSuccess()) {
                    GoogleSignInAccount account = result.getSignInAccount();
                    firebaseAuthWithGoogle(account);
                }
            }
        }
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount account) {
        if (BuildConfig.DEBUG) Log.d(TAG, "firebaseAuthWithGoogle: " + account.getDisplayName());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mfirebaseauth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (BuildConfig.DEBUG) Log.d(TAG, "onComplete: " + task.isSuccessful());

                if (task.isSuccessful()) {
                    String photourl = null;
                    if (account.getPhotoUrl() != null) {
                        photourl = account.getPhotoUrl().toString();
                    }
                    Users user = new Users(account.getDisplayName() , account.getEmail(), photourl,
                            FirebaseAuth.getInstance().getCurrentUser().getUid());

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference userref = database.getReference(constants.USER_KEY);
                    userref.child(account.getEmail().replace(".", ",")).setValue(user, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                }
                            }


                    );
                    if (BuildConfig.DEBUG) {
                        Log.v(TAG, "Authentication Successful");
                        Toast.makeText(MainActivity.this,"Authentication successful",Toast.LENGTH_LONG);

                        startActivity(new Intent(MainActivity.this, NavDrawerActivity.class));

                    } else {
                        if (BuildConfig.DEBUG) {
                            Log.v(TAG, "signInWithCredential", task.getException());
                            Log.v(TAG, "Authentication failed");
                            Toast.makeText(MainActivity.this, "Authentication failed", Toast.LENGTH_LONG).show();
                            signOut();
                        }

                    }
                }
            }
        });
    }
}


