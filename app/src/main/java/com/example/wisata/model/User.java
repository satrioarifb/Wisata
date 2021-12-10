package com.example.wisata.model;

public class User {
    public String Name, Gender, Email;
    public String judul, lokasi, rating, review;

    public User(String judul, String lokasi, String rating, String review){
        this.judul = judul;
        this.lokasi = lokasi;
        this.rating = rating;
        this.review = review;

    }
    public User(String Name, String Gender, String Email){
        this.Name = Name;
        this.Gender = Gender;
        this.Email = Email;

    }


}