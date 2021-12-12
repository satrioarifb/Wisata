package com.example.wisata.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wisata.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    ArrayList<User> list;

    public MyAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_kuliner, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        holder.judul.setText(this.list.get(position).judul);
//        holder.lokasi.setText(this.list.get(position).lokasi);
//        holder.rating.setText(this.list.get(position).rating);
//        holder.review.setText(this.list.get(position).review);
//        holder.upload.setOnClickListener(this.list.get(position).upload);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView judul, lokasi, rating, review;
        Button upload;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            judul = itemView.findViewById(R.id.judul);
            lokasi = itemView.findViewById(R.id.lokasi);
            rating = itemView.findViewById(R.id.rating);
            review = itemView.findViewById(R.id.review);
            upload = itemView.findViewById(R.id.upload);
        }
    }
}
