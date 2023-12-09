package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.myapplication.model.Cinema;
import com.example.myapplication.model.Movie;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CinemaActivity extends AppCompatActivity {

    Movie movie;

    ArrayList<Cinema> cinemas;

    Toolbar myToolbar;

    RecyclerView rvCinemas;

    public Movie getMovie() {
        return movie;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema);

        myToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        movie = getIntent().getParcelableExtra("movie");

        cinemas = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CinemaAdapter cinemaAdapter = new CinemaAdapter(this, cinemas, movie);

        rvCinemas = findViewById(R.id.rvCinemas);
        rvCinemas.setAdapter(cinemaAdapter);
        rvCinemas.setLayoutManager(new LinearLayoutManager(this));

        db.collection("cinemas")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                cinemas.add(new Cinema(document));
                            }
                            cinemaAdapter.setCinemas(cinemas);
                            Log.d("hello", Integer.toString(cinemas.size()));
                        } else {

                        }
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}