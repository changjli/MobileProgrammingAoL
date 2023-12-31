package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.adapter.SeatAdapter;
import com.example.myapplication.model.Screening;
import com.example.myapplication.model.ScreeningTime;
import com.example.myapplication.model.Seat;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ChoosingSeatActivity extends TemplateActivity implements View.OnClickListener {

    ScreeningTime screeningTime;

    Screening screening;

    RecyclerView rvSeats;

    ArrayList<Seat> seats;

    ArrayList<Seat> selectedSeats;

    TextView tvCinemaLocation, tvCinemaStudio;

    Button btnOrder;

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
        setContentView(R.layout.activity_choosing_seat);

        setupToolbar("Choosing seat");
        setupBackBtn();

        screeningTime = getIntent().getExtras().getParcelable("screeningTime");
        screening = getIntent().getExtras().getParcelable("screening");
//        movie = getIntent().getExtras().getParcelable("movie");
//        cinema = getIntent().getExtras().getParcelable("cinema");

        tvCinemaLocation = findViewById(R.id.tvCinemaLocation);
        tvCinemaStudio = findViewById(R.id.tvCinemaStudio);
        tvCinemaLocation.setText(screening.getCinema().getLocation());
        tvCinemaStudio.setText("Studio " + String.valueOf(screening.getStudio()));

        seats = new ArrayList<Seat>();
        selectedSeats = new ArrayList<>();

        btnOrder = findViewById(R.id.btnOrder);
        btnOrder.setOnClickListener(this);

        rvSeats = findViewById(R.id.rvSeats);
        SeatAdapter seatAdapter = new SeatAdapter(this, seats);
        rvSeats.setAdapter(seatAdapter);
        rvSeats.setLayoutManager(new GridLayoutManager(this, 7));
        rvSeats.addItemDecoration(new GridSpaceItemDecoration(7, 30, false));

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
            toOrder.putExtra("screening", screening);
            startActivity(toOrder);
        }
    }
}