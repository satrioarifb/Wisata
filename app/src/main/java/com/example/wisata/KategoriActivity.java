package com.example.wisata;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wisata.adapter.MyAdapter;
import com.example.wisata.model.Request;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class KategoriActivity extends AppCompatActivity {

    public static final String EXTRA_CATEGORY = "EXTRA_CATEGORY";
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("review");
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori);

        TextView judulKategori = findViewById(R.id.txJudul);
        Button btnAdd = findViewById(R.id.btn_add_data);
        RecyclerView rvReview = findViewById(R.id.rv_review);

        String kategori = getIntent().getStringExtra(EXTRA_CATEGORY);

        judulKategori.setText(kategori);
        adapter = new MyAdapter(this);

        rvReview.setLayoutManager(new LinearLayoutManager(this));
        rvReview.setHasFixedSize(true);
        rvReview.setAdapter(adapter);

        databaseReference.orderByChild("kategori").equalTo(kategori).addValueEventListener(reviewListener);
        btnAdd.setOnClickListener(view -> {
            Intent intent = new Intent(KategoriActivity.this, add.class);
            intent.putExtra(EXTRA_CATEGORY, kategori);
            startActivity(intent);
        });
    }

    ValueEventListener reviewListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            ArrayList<Request> result = new ArrayList<>();
            for (DataSnapshot data : snapshot.getChildren()) {
                Request request = data.getValue(Request.class);
                request.setKey(data.getKey());
                result.add(request);
            }
            adapter.setList(result);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
        }
    };
}