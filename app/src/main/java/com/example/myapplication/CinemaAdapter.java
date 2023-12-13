package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.model.Cinema;
import com.example.myapplication.model.Movie;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CinemaAdapter extends RecyclerView.Adapter<CinemaAdapter.CinemaViewHolder> {

    Context context;
    ArrayList<Cinema> cinemas;
    Movie movie;

    public CinemaAdapter(Context context, ArrayList<Cinema> cinemas){
        this.context = context;
        this.cinemas = cinemas;
        this.movie = null;
    }

    public CinemaAdapter(Context context, ArrayList<Cinema> cinemas, Movie movie){
        this.context = context;
        this.cinemas = cinemas;
        this.movie = movie;
    }

    public void setCinemas(ArrayList<Cinema> cinemas) {
        this.cinemas = cinemas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CinemaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_cinema, parent, false);

        return new CinemaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CinemaViewHolder holder, int position) {
        holder.tvCinemaLocation.setText(cinemas.get(position).getLocation());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toScreening = new Intent(context, ScreeningActivity.class);
                if(movie != null){
                    toScreening.putExtra("movie", movie);
                }
                toScreening.putExtra("cinema", cinemas.get(holder.getAdapterPosition()));
                context.startActivity(toScreening);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cinemas.size();
    }

    public static class CinemaViewHolder extends RecyclerView.ViewHolder {

        TextView tvCinemaLocation;

        public CinemaViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            tvCinemaLocation = view.findViewById(R.id.tvCinemaLocation);
        }
    }
}
