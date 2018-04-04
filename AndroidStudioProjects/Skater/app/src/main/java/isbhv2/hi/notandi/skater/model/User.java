package isbhv2.hi.notandi.skater.model;

/*
Módelklasi fyrir innskráðan notanda.
 */

import android.graphics.Point;

/**
 * Created by Bjarki on 14.2.2018.
 */

public class User {
    public String username = "Guest";
    public String email;
    public String spot;
    public String lastSpot;
    public int photoId;
    //public Point location;

    public User(String username, String email, String spot) {
        this.username = username;
        this.email = email;
        this.spot = spot;
        //this.location = location;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSpot() {
        return spot;
    }

    public void setLastSpot(String lastSpot) {
        this.lastSpot = lastSpot;
    }

    public String getLastSpot() {
        return lastSpot;
    }

    public void setSpot(String spot) {
        this.spot = spot;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }


}
