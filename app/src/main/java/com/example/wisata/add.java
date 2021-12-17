package com.example.wisata;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wisata.model.Request;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

public class add extends AppCompatActivity {

    EditText judul, lokasi, rating, review;
    Button upload, save;
    ImageView imgHolder;

    private static final int REQUEST_PICK_PHOTO = 1;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("review");
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    private Uri photo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        String kategori = getIntent().getStringExtra(KategoriActivity.EXTRA_CATEGORY);

        judul = (EditText) findViewById(R.id.judul);
        lokasi = (EditText) findViewById(R.id.lokasi);
        rating = (EditText) findViewById(R.id.rating);
        review = (EditText) findViewById(R.id.review);
        imgHolder = (ImageView) findViewById(R.id.imgHolder);
        upload = (Button) findViewById(R.id.upload);
        save = (Button) findViewById(R.id.update);

        upload.setOnClickListener(v -> {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, REQUEST_PICK_PHOTO);
        });

        save.setOnClickListener(v -> {

            if (photo != null) {
                Save(judul.getText().toString(),
                        lokasi.getText().toString(),
                        rating.getText().toString(),
                        review.getText().toString(),
                        kategori);
            } else {
                Toast.makeText(this, "Photo is required", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            photo = data.getData();
            imgHolder.setImageURI(photo);
        }
    }

    private void Save(String judul, String lokasi, String rating, String review, String kategori) {
        String photoId = UUID.randomUUID().toString();
        StorageReference ref = storageRef.child(photoId);
        ref.putFile(photo).addOnSuccessListener(taskSnapshot -> {
            ref.getDownloadUrl().addOnSuccessListener(uri -> {
                Request request = new Request(judul, lokasi, rating, review, uri.toString(), kategori);
                String reviewId = UUID.randomUUID().toString();
                databaseReference.child(reviewId).setValue(request)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(
                                        add.this,
                                        "Data Berhasil Ditambahkan",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
            });
        });
    }
}