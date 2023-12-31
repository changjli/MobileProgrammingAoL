package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.MovieActivity;
import com.example.myapplication.R;
import com.example.myapplication.model.Movie;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context context;
    private ArrayList<Movie> movies;

    public MovieAdapter(Context context, ArrayList<Movie> movies){
        this.context = context;
        this.movies = movies;
    }

    // rebuild recycle view after fetching data
    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;

        // setState
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
//        holder.ivMovie.setImageResource(R.drawable.a52759c832de761477d479d7cf0c53e5);

        Movie movie = movies.get(position);

        // load images
        Glide
                .with(holder.itemView)
                .load(movie.getImageUrl())
                .fitCenter()
                .into(holder.ivMovie);

        holder.tvMovie.setText(movie.getTitle());
        holder.tvMovieRated.setText(String.valueOf(movie.getRated()) + "+");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMovie = new Intent(v.getContext(), MovieActivity.class);
                toMovie.putExtra("movie", movie);
                v.getContext().startActivity(toMovie);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView ivMovie;

        TextView tvMovie, tvMovieRated;

        public MovieViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            ivMovie = view.findViewById(R.id.ivMovie);

            tvMovie = view.findViewById(R.id.tvMovie);

            tvMovieRated = view.findViewById(R.id.tvMovieRated);
        }


    }

}
