package com.example.wisata.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wisata.model.Request;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wisata.R;
import com.example.wisata.model.User;
import com.example.wisata.update;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    ArrayList<Request> list = new ArrayList<>();

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("review");

    public MyAdapter(Context context) {
        this.context = context;
    }

    public void setList(ArrayList<Request> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_review, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(list.get(position), databaseReference);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView judul, lokasi, rating, review;
        ImageView gambar, btnEdit, btnDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            judul = itemView.findViewById(R.id.txJudulR);
            lokasi = itemView.findViewById(R.id.txLokasi);
            rating = itemView.findViewById(R.id.isiRating);
            review = itemView.findViewById(R.id.isiReview);
            gambar = itemView.findViewById(R.id.imgReview);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }

        public void bind(Request data, DatabaseReference db) {
            judul.setText(data.judul);
            lokasi.setText(data.lokasi);
            rating.setText(data.rating);
            review.setText(data.review);
            Glide.with(itemView.getContext()).load(data.photoUrl).into(gambar);
            btnDelete.setOnClickListener(view -> {
                db.child(data.key).removeValue();
                Toast.makeText(
                        itemView.getContext(),
                        data.judul + " Berhasil Dihapus",
                        Toast.LENGTH_SHORT)
                        .show();
            });
            btnEdit.setOnClickListener(view -> {
                Intent intent = new Intent(itemView.getContext(), update.class);
                intent.putExtra(update.EXTRA_KEY, data.key);
                itemView.getContext().startActivity(intent);
            });
        }
    }
}
