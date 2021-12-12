package com.example.wisata;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.example.wisata.model.Request;
import com.example.wisata.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class add extends AppCompatActivity {

    EditText judul, lokasi, rating, review;
    Button upload, save;
    ImageView imgHolder;

    private static final int REQUEST_PICK_PHOTO = 1;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        mAuth = FirebaseAuth.getInstance();
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

    FirebaseDatabase.getInstance().getReference("review")
            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
            .setValue(request).addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
            if (task.isSuccessful()){
                Toast.makeText(add.this, "Data berhasil disimpan", Toast.LENGTH_LONG).show();

                startActivity(new Intent(add.this, MainActivity.class));
            } else {
                Toast.makeText(add.this, "Data gagal disimpan, coba lagi!", Toast.LENGTH_LONG).show();

            }
        }
    });
    }

//    public void Save(View v) {
//        Intent intent = new Intent(add.this, update.class);
//        startActivity(intent);
//    }
}