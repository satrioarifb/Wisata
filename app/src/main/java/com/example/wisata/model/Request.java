package com.example.wisata.model;

import android.widget.Button;

public class Request {
    public String judul, lokasi, rating, review;
   // public Button upload;

    public Request(){

    }
    public Request(String judul, String lokasi, String rating, String review){
        this.judul = judul;
        this.lokasi = lokasi;
        this.rating = rating;
        this.review = review;
       // this.upload = upload;
    }

}
