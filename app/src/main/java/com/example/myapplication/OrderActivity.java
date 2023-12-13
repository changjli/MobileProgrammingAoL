package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.model.Cinema;
import com.example.myapplication.model.Movie;
import com.example.myapplication.model.ScreeningTime;
import com.example.myapplication.model.Seat;
import com.example.myapplication.model.TransactionHeader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener {

    Movie movie;
    Cinema cinema;
    ScreeningTime screeningTime;
    ArrayList<Seat> selectedSeats;
    FirebaseFirestore db;
    TextView tvMovie, tvCinema, tvScreeningTime;
    RecyclerView rvSelectedSeats;
    Button btnTransaction;
    FirebaseAuth mauth;
    FirebaseUser muser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        movie = getIntent().getExtras().getParcelable("movie");
        cinema = getIntent().getExtras().getParcelable("cinema");
        screeningTime = getIntent().getExtras().getParcelable("screeningTime");
        selectedSeats = getIntent().getExtras().getParcelableArrayList("selectedSeats");

        tvMovie = findViewById(R.id.tvMovie);
        tvCinema = findViewById(R.id.tvCinema);
        tvScreeningTime = findViewById(R.id.tvScreeningTime);

        tvMovie.setText(movie.getTitle());
        tvCinema.setText(cinema.getLocation());
        SimpleDateFormat formatter = new SimpleDateFormat("MM dd, yyyy");
        tvScreeningTime.setText(formatter.format(screeningTime.getTime()));

        rvSelectedSeats = findViewById(R.id.rvSelectedSeats);
        rvSelectedSeats.setAdapter(new SeatAdapter(this, selectedSeats));
        rvSelectedSeats.setLayoutManager(new LinearLayoutManager(this));

        btnTransaction = findViewById(R.id.btnTransaction);

        btnTransaction.setOnClickListener(this);

        mauth = FirebaseAuth.getInstance();
        muser = mauth.getCurrentUser();

        db = FirebaseFirestore.getInstance();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnTransaction){
            db.collection("transactionHeaders")
                    .add(new TransactionHeader(muser.getUid(), db.document(screeningTime.getRef()), Calendar.getInstance().getTime(), 50, "ovo"))
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if(task.isSuccessful()){
                                DocumentReference ref = task.getResult();
                                for(Seat seat : selectedSeats){
                                    ref.collection("transactionDetails")
                                            .add(seat).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                                    db.document(seat.getRef()).update("availability", false);
                                                }
                                            });
                                }
                                Toast.makeText(OrderActivity.this, "transaction successful", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}