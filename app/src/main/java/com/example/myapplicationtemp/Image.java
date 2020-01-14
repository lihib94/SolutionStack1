package com.example.myapplicationtemp;

import java.util.ArrayList;
import java.util.List;

public class Image {

    private String image;
    private String user;

    public Image(String im, String us) {

        this.image = im;
        this.user = us;
    }

    public Image(String im) {
        this.image = im;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}