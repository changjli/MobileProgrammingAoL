package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.model.Cinema;
import com.example.myapplication.model.Movie;
import com.example.myapplication.model.ScreeningTime;
import com.example.myapplication.model.Seat;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class BookingActivity extends AppCompatActivity implements View.OnClickListener {

    ScreeningTime screeningTime;

    RecyclerView rvSeats;

    ArrayList<Seat> selectedSeats;

    Button btnOrder;

    Movie movie;

    Cinema cinema;

    public ArrayList<Seat> getSelectedSeats() {
        return selectedSeats;
    }

    public void setSelectedSeats(ArrayList<Seat> selectedSeats) {
        this.selectedSeats = selectedSeats;
    }

    public void addSelectedSeats(Seat seat){
        this.selectedSeats.add(seat);
    }

    public void removeSelectedSeats(Seat seat){
        this.selectedSeats.remove(seat);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        selectedSeats = new ArrayList<>();

        btnOrder = findViewById(R.id.btnOrder);

        btnOrder.setOnClickListener(this);

        screeningTime = getIntent().getExtras().getParcelable("screeningTime");
        Log.v("hello", DateFormatHelper.toString(screeningTime.getTime(), "dd-MM-yyyy"));
        movie = getIntent().getExtras().getParcelable("movie");
        cinema = getIntent().getExtras().getParcelable("cinema");

        ArrayList<Seat> seats = new ArrayList<Seat>();

        rvSeats = findViewById(R.id.rvSeats);
        SeatAdapter seatAdapter = new SeatAdapter(this, seats);
        rvSeats.setAdapter(seatAdapter);
        rvSeats.setLayoutManager(new GridLayoutManager(this, 10));

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.document(screeningTime.getRef());

        docRef.collection("seats")
                .orderBy("number")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                seats.add(new Seat(document));
                            }
                        } else {

                        }
                        seatAdapter.setSeats(seats);
                    }
                });
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnOrder){
//            for(Seat seat : selectedSeats){
//                Log.v("selected", seat.getRef());
//            }
            Intent toOrder = new Intent(this, OrderActivity.class);
            toOrder.putExtra("selectedSeats", selectedSeats);
            toOrder.putExtra("screeningTime", screeningTime);
            toOrder.putExtra("movie", movie);
            toOrder.putExtra("cinema", cinema);
            startActivity(toOrder);
        }
    }
}