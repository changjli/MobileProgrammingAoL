package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.model.Movie;

public class MovieActivity extends AppCompatActivity implements View.OnClickListener {

    Movie movie;

    Toolbar myToolbar;

    ImageView ivMovie;

    TextView tvMovieTitle, tvMovieDuration, tvMovieDescription;

    Button btnBookTicket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        myToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        movie = getIntent().getParcelableExtra("movie");

        ivMovie = findViewById(R.id.ivMovie);

        tvMovieTitle = findViewById(R.id.tvMovieTitle);
        tvMovieDuration = findViewById(R.id.tvMovieDuration);
        tvMovieDescription = findViewById(R.id.tvMovieDescription);

        btnBookTicket = findViewById(R.id.btnBookTicket);

        Glide
                .with(this)
                .load(movie.getImageUrl())
                .centerCrop()
                .into(ivMovie);

        tvMovieTitle.setText(movie.getTitle());
        tvMovieDuration.setText(String.valueOf(movie.getDuration()));
        tvMovieDescription.setText(movie.getDescription());

        btnBookTicket.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnBookTicket){
            Intent toCinema = new Intent(this, CinemaActivity.class);
            toCinema.putExtra("movie", movie);
            startActivity(toCinema);
        }
    }
}