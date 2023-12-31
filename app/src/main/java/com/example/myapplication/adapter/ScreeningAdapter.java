package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.MovieActivity;
import com.example.myapplication.R;
import com.example.myapplication.model.Screening;
import com.example.myapplication.model.ScreeningTime;

import java.util.ArrayList;

public class ScreeningAdapter extends RecyclerView.Adapter<ScreeningAdapter.ScreeningViewHolder> {
    Context context;
    ArrayList<Screening> screenings;

    public ScreeningAdapter(Context context, ArrayList<Screening> screenings){
        this.context = context;
        this.screenings = screenings;
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
        Glide
                .with(holder.itemView)
                .load(screenings.get(position).getMovie().getImageUrl())
                .centerCrop()
                .into(holder.ivMovieImageUrl);

        holder.tvMovieTitle.setText(screenings.get(position).getMovie().getTitle());
        holder.tvMovieDuration.setText(screenings.get(position).getMovie().getDuration() + " min");
        holder.tvMovieRated.setText(String.valueOf(screenings.get(position).getMovie().getRated()) + "+");
        holder.tvMovieGenre.setText(screenings.get(position).getMovie().getGenre());

        holder.ivMovieImageUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMovie = new Intent(context, MovieActivity.class);
                toMovie.putExtra("movie", screenings.get(holder.getAdapterPosition()).getMovie());
                toMovie.putExtra("disabled", true);
                context.startActivity(toMovie);
            }
        });

        holder.rvScreeningTimes.setAdapter(new ScreeningTimeAdapter(context, screenings.get(position)));
        holder.rvScreeningTimes.setLayoutManager(new GridLayoutManager(context, 5));
    }

    @Override
    public int getItemCount() {
        return screenings.size();
    }

    public static class ScreeningViewHolder extends RecyclerView.ViewHolder {

        ImageView ivMovieImageUrl;
        TextView tvMovieTitle, tvMovieDuration, tvMovieRated, tvMovieGenre;
        RecyclerView rvScreeningTimes;

        public ScreeningViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            ivMovieImageUrl = view.findViewById(R.id.ivMovieImageUrl);
            tvMovieTitle = view.findViewById(R.id.tvMovieTitle);
            tvMovieRated = view.findViewById(R.id.tvMovieRated);
            tvMovieGenre = view.findViewById(R.id.tvMovieGenre);
            tvMovieDuration = view.findViewById(R.id.tvMovieDuration);
            rvScreeningTimes = view.findViewById(R.id.rvScreeningTimes);
        }
    }
}
