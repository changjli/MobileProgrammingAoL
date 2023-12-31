package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.myapplication.adapter.CinemaAdapter;
import com.example.myapplication.model.Cinema;
import com.example.myapplication.model.Movie;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CinemaActivity extends TemplateActivity implements SearchView.OnQueryTextListener {

    Movie movie;

    ArrayList<Cinema> cinemas;

    SearchView svCinema;

    RecyclerView rvCinemas;

    CinemaAdapter cinemaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema);

       setupToolbar("Cinemas");
       setupBackBtn();

        movie = getIntent().getParcelableExtra("movie");

        // rv cinemas
        rvCinemas = findViewById(R.id.rvCinemas);
        cinemas = new ArrayList<>();
        cinemaAdapter = new CinemaAdapter(this, cinemas, movie);
        rvCinemas.setAdapter(cinemaAdapter);
        rvCinemas.setLayoutManager(new LinearLayoutManager(this));
        // end

        svCinema = findViewById(R.id.svCinema);
        svCinema.setQueryHint("Search for cinema");
        svCinema.setOnQueryTextListener(this);

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
                        } else {

                        }
                    }
                });
    }

    /**
     * Called when the user submits the query. This could be due to a key press on the
     * keyboard or due to pressing a submit button.
     * The listener can override the standard behavior by returning true
     * to indicate that it has handled the submit request. Otherwise return false to
     * let the SearchView handle the submission by launching any associated intent.
     *
     * @param query the query text that is to be submitted
     * @return true if the query has been handled by the listener, false to let the
     * SearchView perform the default action.
     */
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    /**
     * Called when the query text is changed by the user.
     *
     * @param newText the new content of the query text field.
     * @return false if the SearchView should perform the default action of showing any
     * suggestions if available, true if the action was handled by the listener.
     */
    @Override
    public boolean onQueryTextChange(String newText) {
        ArrayList<Cinema> temp = new ArrayList<>();
        for(Cinema cinema : cinemas){
            if(cinema.getLocation().toLowerCase().contains(newText.toLowerCase())){
                temp.add(cinema);
                Log.v("hello", "world");
                cinemaAdapter.setCinemas(temp);
            }
        }
        return false;
    }

}