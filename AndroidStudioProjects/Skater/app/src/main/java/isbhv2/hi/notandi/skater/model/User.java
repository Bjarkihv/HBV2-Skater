package isbhv2.hi.notandi.skater.model;

import android.graphics.Point;

/**
 * Created by Bjarki on 14.2.2018.
 */

public class User {
    public String username = "Guest";
    public String email;
    public int photoId;
    //public Point location;

    public User(String username, String email, int photoId) {
        this.username = username;
        this.email = email;
        this.photoId = photoId;
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

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    /*
    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }
    */



}
