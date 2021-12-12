package com.example.wisata;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.wisata.model.Request;

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
        Request request = new Request(judul, lokasi, rating, review);
        request.judul = judul;
        request.lokasi = lokasi;
        request.rating = rating;
        request.review = review;
//        request.upload = upload;
        finish();
    }

//    public void Save(View v) {
//        Intent intent = new Intent(add.this, update.class);
//        startActivity(intent);
//    }
}