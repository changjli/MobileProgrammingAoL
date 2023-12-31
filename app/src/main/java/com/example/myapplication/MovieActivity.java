package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.model.Movie;

public class MovieActivity extends TemplateActivity implements View.OnClickListener {

    Movie movie;

    ImageView ivMovie;

    TextView tvMovieTitle, tvMovieDuration, tvMovieDescription, tvMovieProducer, tvMovieDirector, tvMovieWriter, tvMovieDistributor,
    tvMovieRated, tvMovieGenre, tvMovieCasts;

    Button btnBookTicket;
    Boolean disabled;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        movie = getIntent().getParcelableExtra("movie");
        disabled = getIntent().getBooleanExtra("disabled", false);

        super.setupToolbar(movie.getTitle());
        super.setupBackBtn();

        ivMovie = findViewById(R.id.ivMovie);

        tvMovieTitle = findViewById(R.id.tvMovieTitle);
        tvMovieRated = findViewById(R.id.tvMovieRated);
        tvMovieGenre = findViewById(R.id.tvMovieGenre);
        tvMovieDuration = findViewById(R.id.tvMovieDuration);
        tvMovieDescription = findViewById(R.id.tvMovieDescription);
        tvMovieProducer = findViewById(R.id.tvMovieProducer);
        tvMovieDirector = findViewById(R.id.tvMovieDirector);
        tvMovieWriter = findViewById(R.id.tvMovieWriter);
        tvMovieDistributor = findViewById(R.id.tvMovieDistributor);
        tvMovieCasts = findViewById(R.id.tvMovieCasts);

        btnBookTicket = findViewById(R.id.btnBookTicket);

        Glide
                .with(this)
                .load(movie.getImageUrl())
                .centerCrop()
                .into(ivMovie);
        tvMovieTitle.setText(movie.getTitle());
        tvMovieRated.setText(String.valueOf(movie.getRated()) + "+");
        tvMovieGenre.setText(movie.getGenre());
        tvMovieDuration.setText(String.valueOf(movie.getDuration()) + " minute(s)");
        tvMovieProducer.setText(movie.getProducer());
        tvMovieDirector.setText(movie.getDirector());
        tvMovieWriter.setText(movie.getWriter());
        tvMovieDistributor.setText(movie.getDistributor());
        tvMovieDescription.setText(movie.getDescription());
        tvMovieCasts.setText(movie.getCasts());

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
            if(!disabled){
                Intent toCinema = new Intent(this, CinemaActivity.class);
                toCinema.putExtra("movie", movie);
                startActivity(toCinema);
            }else{
                Toast.makeText(this, "Cannot book again", Toast.LENGTH_LONG).show();
            }

        }
    }
}