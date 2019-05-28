package com.example.moodymusicappfinal.models;

public class Users {

    private String users;
    private String email;
    private String photourl;
    private String uid;


    public Users(){

    }

    public Users(String users) {
        this.users = users;
    }

    public Users(String users, String email, String photourl, String uid) {
        this.users = users;
        this.email = email;
        this.photourl = photourl;
        this.uid = uid;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotourl() {
        return photourl;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
