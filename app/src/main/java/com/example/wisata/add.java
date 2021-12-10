package com.example.wisata;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;

import com.example.wisata.model.Artikel;
import com.example.wisata.model.User;

public class add extends AppCompatActivity {

    EditText judul, lokasi, rating, review;
    Button upload, save;
    ImageView imgHolder;

    private static final int REQUEST_PICK_PHOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        judul = (EditText) findViewById(R.id.judul);
        lokasi = (EditText) findViewById(R.id.lokasi);
        rating = (EditText) findViewById(R.id.rating);
        review = (EditText) findViewById(R.id.review);
        imgHolder = (ImageView) findViewById(R.id.imgHolder);
        upload = (Button) findViewById(R.id.upload);
        save = (Button) findViewById(R.id.save);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, REQUEST_PICK_PHOTO);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save(judul.getText().toString(), lokasi.getText().toString(), rating.getText().toString(), review.getText().toString());
                Intent intent = new Intent(add.this, update.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        imgHolder.setImageURI(uri);
    }

    private void Save (String judul, String lokasi, String rating, String review){

        User user = new User(judul, lokasi, rating, review);
        user.judul = judul;
        user.lokasi = lokasi;
        user.rating = rating;
        user.review = review;
        finish();
    }

//    public void Save(View v) {
//        Intent intent = new Intent(add.this, update.class);
//        startActivity(intent);
//    }
}