package com.example.wisata;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MenuUtama extends AppCompatActivity implements View.OnClickListener {

    private ImageView makanan;
    private ImageView pantai;
    private ImageView religi;
    private ImageView kebun;
    private ImageView tamanbermain;
    private ImageView hotel;

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
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.makanan:
                startActivity(new Intent(this, wisata_kuliner.class));
                break;
            case R.id.Pantai:
                startActivity(new Intent(this, pantai.class));
                break;
            case R.id.religi:
                startActivity(new Intent(this, wisata_religi.class));
                break;
            case R.id.kebun:
                startActivity(new Intent(this, kebun.class));
                break;
            case R.id.tamanBermain:
                startActivity(new Intent(this, taman_bermain.class));
                break;
            case R.id.hotel:
                startActivity(new Intent(this, hotel.class));
                break;

        }
    }
}
