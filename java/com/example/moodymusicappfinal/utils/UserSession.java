package com.example.moodymusicappfinal.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSession {
    SharedPreferences sharedPreferences;
    Context context;
    public String emailadd;

    public void remove(){
        sharedPreferences.edit().clear().commit();
    }



    public String getEmailAdd() {
        emailadd = sharedPreferences.getString("userdata","");

        return emailadd;
    }

    public void setEmailAdd(String email) {
        this.emailadd = email;
        sharedPreferences.edit().putString("userdata",emailadd).commit();

    }

    public UserSession(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences("userinfo",Context.MODE_PRIVATE);

    }
}
