package com.example.wisata;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wisata.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuUtama extends AppCompatActivity implements View.OnClickListener {

    private ImageView makanan;
    private ImageView pantai;
    private ImageView religi;
    private ImageView kebun;
    private ImageView tamanbermain;
    private ImageView hotel;
    private ImageView profil;

    private FirebaseUser user;
    private DatabaseReference reference;

    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuutama);

        makanan = (ImageView) findViewById(R.id.makanan);
        makanan.setOnClickListener((View.OnClickListener) this);

        pantai = (ImageView) findViewById(R.id.Pantai);
        pantai.setOnClickListener((View.OnClickListener) this);

        religi = (ImageView) findViewById(R.id.religi);
        religi.setOnClickListener((View.OnClickListener) this);

        kebun = (ImageView) findViewById(R.id.kebun);
        kebun.setOnClickListener((View.OnClickListener) this);

        tamanbermain = (ImageView) findViewById(R.id.tamanBermain);
        tamanbermain.setOnClickListener((View.OnClickListener) this);

        hotel = (ImageView) findViewById(R.id.hotel);
        hotel.setOnClickListener((View.OnClickListener) this);

        profil = (ImageView) findViewById(R.id.profil);
        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuUtama.this, profile.class));
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users");
        userID = user.getUid();

        final TextView greetingTextView = (TextView) findViewById(R.id.txHalo);
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofile = snapshot.getValue(User.class);

                if (userprofile != null){
                    String fullName = userprofile.Name;

                    greetingTextView.setText("Halo, " + fullName + "!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MenuUtama.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, KategoriActivity.class);
        String kategori = "";

        switch (view.getId()){
            case R.id.makanan:
                kategori = "Makanan";
                break;
            case R.id.Pantai:
                kategori = "Pantai";
                break;
            case R.id.religi:
                kategori = "Religi";
                break;
            case R.id.kebun:
                kategori = "Kebun";
                break;
            case R.id.tamanBermain:
                kategori = "Taman Bermain";
                break;
            case R.id.hotel:
                kategori = "Hotel";
                break;
        }
        intent.putExtra(KategoriActivity.EXTRA_CATEGORY, kategori);
        startActivity(intent);

    }
}
