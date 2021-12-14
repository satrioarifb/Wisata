package com.example.wisata;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wisata.model.Request;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

public class update extends AppCompatActivity {
    public static final String EXTRA_KEY = "EXTRA_KEY";
    private static final int REQUEST_PICK_PHOTO = 1;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("review");
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    EditText judul, lokasi, rating, review;
    Button upload, save;
    ImageView imgHolder;
    String key;
    Request data;
    private Uri photo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        key = getIntent().getStringExtra(EXTRA_KEY);

        judul = findViewById(R.id.editTextTextPersonName);
        lokasi = findViewById(R.id.lokasi);
        rating = findViewById(R.id.rating);
        review = findViewById(R.id.review);
        imgHolder = findViewById(R.id.imgHolder);
        upload = findViewById(R.id.upload);
        save = findViewById(R.id.update);

        upload.setOnClickListener(v -> {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, REQUEST_PICK_PHOTO);
        });

        databaseReference.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data = snapshot.getValue(Request.class);
                Log.d("hehehe", snapshot.toString());
                Log.d("hehehe", data.lokasi.toString());
                judul.setText(data.judul);
                lokasi.setText(data.lokasi);
                rating.setText(data.rating);
                review.setText(data.review);
                Glide.with(update.this).load(data.photoUrl).into(imgHolder);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        save.setOnClickListener(view -> {
            Request newData = new Request(judul.getText().toString(),
                    lokasi.getText().toString(),
                    rating.getText().toString(),
                    review.getText().toString(),
                    data.photoUrl,
                    data.kategori);
            if (photo != null) {
                String photoId = UUID.randomUUID().toString();
                StorageReference ref = storageRef.child(photoId);
                ref.putFile(photo).addOnSuccessListener(taskSnapshot -> {
                    ref.getDownloadUrl().addOnSuccessListener(uri -> {
                        newData.photoUrl = uri.toString();
                        updateData(newData);
                    });
                });
            } else {
                updateData(newData);
            }
        });
    }

    private void updateData(Request data) {
        databaseReference.child(key).setValue(data);
        Toast.makeText(this, "Data Berhasil di Update", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            photo = data.getData();
            imgHolder.setImageURI(photo);
        }
    }
}