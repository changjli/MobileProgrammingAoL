package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.example.myapplication.model.Cinema;
import com.example.myapplication.model.Movie;
import com.example.myapplication.model.Screening;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ScreeningActivity extends AppCompatActivity {

    Movie movie;
    Cinema cinema;

    ArrayList<Screening> screenings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screening);

        movie = getIntent().getParcelableExtra("movie");
        cinema = getIntent().getParcelableExtra("cinema");

        screenings = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference movieRef = db.collection("movies").document(movie.getId());
        DocumentReference cinemaRef = db.collection("cinemas").document(cinema.getId());

        db.collection("screenings")
                .whereEqualTo("movieId", movieRef)
                .whereEqualTo("cinemaId", cinemaRef)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Screening screening = new Screening(document);
                                document.getDocumentReference("movieId")
                                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if(task.isSuccessful()){
                                                    DocumentSnapshot document = task.getResult();
                                                    screening.setMovie(new Movie(document));
                                                    Log.d("oncomplete", "onComplete: " + screening.getMovie().getId());
                                                }
                                            }
                                        });

                            }
                        } else {

                        }
                    }
                });



    }
}