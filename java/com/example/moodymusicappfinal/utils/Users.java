package com.example.moodymusicappfinal.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Users {
    Context context1;
    SharedPreferences sharedPreferences;
    public String name;

    public Users(Context context){
        context1=context;
        sharedPreferences = context1.getSharedPreferences("userinfo",Context.MODE_PRIVATE);

    }
    public void  removeUser(){
        sharedPreferences.edit().clear().commit();
    }

    public String getName() {
       name = sharedPreferences.getString("userdata","");
        return name;
    }

    public void setName(String namea) {
        name = namea;
        sharedPreferences.edit().putString("userdata",name).commit();
    }




}
