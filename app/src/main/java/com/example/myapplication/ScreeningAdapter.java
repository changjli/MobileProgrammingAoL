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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.model.Cinema;
import com.example.myapplication.model.Movie;
import com.example.myapplication.model.Screening;
import com.example.myapplication.model.ScreeningTime;

import java.util.ArrayList;
import java.util.Date;

public class ScreeningAdapter extends RecyclerView.Adapter<ScreeningAdapter.ScreeningViewHolder> {
    Context context;
    ArrayList<Screening> screenings;
    Movie movie;
    Cinema cinema;

    public ScreeningAdapter(Context context, ArrayList<Screening> screenings, Movie movie, Cinema  cinema){
        this.context = context;
        this.screenings = screenings;
        this.cinema = cinema;
        this.movie = movie;
    }

    public void setScreenings(ArrayList<Screening> screenings) {
        this.screenings = screenings;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ScreeningViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_screening, parent, false);

        return new ScreeningViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScreeningViewHolder holder, int position) {
        holder.tvScreening.setText(screenings.get(position).getId());

        ArrayList<ScreeningTime> time = screenings.get(position).getTime();
        holder.rvTime.setAdapter(new TimeAdapter(context, time, movie, cinema));
        holder.rvTime.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public int getItemCount() {
        return screenings.size();
    }

    public static class ScreeningViewHolder extends RecyclerView.ViewHolder {

        TextView tvScreening;

        RecyclerView rvTime;

        public ScreeningViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            tvScreening = view.findViewById(R.id.tvScreening);

            rvTime = view.findViewById(R.id.rvTime);
        }
    }
}
