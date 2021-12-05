package com.example.wisata;

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

public class add extends AppCompatActivity {

    EditText judul, lokasi, rating, review;
    Button upload, save;
    ImageView img;

    private String mediaPath;
    private String postPath;

    private static final int REQUEST_PICK_PHOTO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        judul = (EditText) findViewById(R.id.judul);
        lokasi = (EditText) findViewById(R.id.lokasi);
        rating = (EditText) findViewById(R.id.rating);
        review = (EditText) findViewById(R.id.review);
        upload = (Button) findViewById(R.id.upload);
        save = (Button) findViewById(R.id.save);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery, REQUEST_PICK_PHOTO);
            }
        });
    }

    //akses izin ambil gambar
    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_PICK_PHOTO) {
                if (data != null) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cs = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    assert cs != null;
                    cs.moveToFirst();
                    int columnIndex = cs.getColumnIndex(filePathColumn[0]);

                    mediaPath = cs.getString(columnIndex);
                    img.setImageURI(data.getData());
                    cs.close();

                    postPath = mediaPath;
                }
            }
        }
    }

    //simpan
}