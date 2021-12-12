package com.example.wisata.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wisata.model.Request;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wisata.R;
import com.example.wisata.model.User;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    ArrayList<Request> list = new ArrayList<>();

    public MyAdapter(Context context) {
        this.context = context;
    }

    public void setList(ArrayList<Request> list){
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
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView judul, lokasi, rating, review;
        ImageView gambar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            judul = itemView.findViewById(R.id.txJudulR);
            lokasi = itemView.findViewById(R.id.txLokasi);
            rating = itemView.findViewById(R.id.isiRating);
            review = itemView.findViewById(R.id.isiReview);
            gambar = itemView.findViewById(R.id.imgReview);
        }

        public void bind(Request data){
            judul.setText(data.judul);
            lokasi.setText(data.lokasi);
            rating.setText(data.rating);
            review.setText(data.review);
            Glide.with(itemView.getContext()).load(data.photoUrl).into(gambar);
        }
    }
}
