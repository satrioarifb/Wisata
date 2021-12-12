package com.example.wisata.model;

public class Request {
    public String judul, lokasi, rating, review, photoUrl, kategori;

    public Request(){

    }

    public Request(String judul, String lokasi, String rating, String review, String photoUrl, String kategori){
        this.judul = judul;
        this.lokasi = lokasi;
        this.rating = rating;
        this.review = review;
        this.photoUrl = photoUrl;
        this.kategori = kategori;
    }

}
