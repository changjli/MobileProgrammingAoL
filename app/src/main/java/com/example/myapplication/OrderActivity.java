package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.adapter.SeatAdapter;
import com.example.myapplication.model.Promo;
import com.example.myapplication.model.Screening;
import com.example.myapplication.model.ScreeningTime;
import com.example.myapplication.model.Seat;
import com.example.myapplication.model.TransactionHeader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;

public class OrderActivity extends TemplateActivity implements View.OnClickListener {
    Screening screening;
    ScreeningTime screeningTime;
    ArrayList<Seat> selectedSeats;
    TextView tvMovieTitle, tvCinemaLocation, tvScreeningDate, tvScreeningTime, tvScreeningStudio, tvTicketPrice, tvTicketTotalPrice,
    tvPromo, tvTotalPrice;
    ImageView ivMovieImageUrl, btnCheckPromo;
    RecyclerView rvSelectedSeats;
    EditText etPromo;
    Spinner paymentSpinner;
    Button btnTransaction;
    FirebaseUser muser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        setupToolbar("Order");
        setupBackBtn();

        screening = getIntent().getExtras().getParcelable("screening");
        screeningTime = getIntent().getExtras().getParcelable("screeningTime");
        selectedSeats = getIntent().getExtras().getParcelableArrayList("selectedSeats");

        ivMovieImageUrl = findViewById(R.id.ivMovieImageUrl);
        tvMovieTitle = findViewById(R.id.tvMovieTitle);
        tvCinemaLocation = findViewById(R.id.tvCinemaLocation);
        tvScreeningDate = findViewById(R.id.tvScreeningDate);
        tvScreeningTime = findViewById(R.id.tvScreeningTime);
        tvScreeningStudio = findViewById(R.id.tvScreeningStudio);
        tvTicketPrice = findViewById(R.id.tvTicketPrice);
        tvTicketTotalPrice = findViewById(R.id.tvTicketTotalPrice);
        tvPromo = findViewById(R.id.tvPromo);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);

        Glide
                .with(this)
                .load(screening.getMovie().getImageUrl())
                .centerCrop()
                .into(ivMovieImageUrl);
        tvMovieTitle.setText(screening.getMovie().getTitle());
        tvCinemaLocation.setText("Location : " + screening.getCinema().getLocation());
        tvScreeningDate.setText("Date : " + DateFormatHelper.toString(screeningTime.getTime(), "MM dd, yyyy"));
        tvScreeningStudio.setText("Studio" + String.valueOf(screening.getStudio()));
        tvScreeningTime.setText("Time : " + DateFormatHelper.toString(screeningTime.getTime(), "HH:mm"));
        tvTicketPrice.setText(String.format("%d x %d", screening.getPrice(), selectedSeats.size()));
        tvTicketTotalPrice.setText(String.format("%d", screening.getPrice() * selectedSeats.size()));
        tvPromo.setText("0%");
        tvTotalPrice.setText(String.format("%d", screening.getPrice() * selectedSeats.size()));

        etPromo = findViewById(R.id.etPromo);
        btnCheckPromo= findViewById(R.id.btnCheckPromo);
        btnCheckPromo.setOnClickListener(this);

        paymentSpinner = findViewById(R.id.paymentSpinner);
        String[] payments = new String[]{"mbanking", "gopay", "shopeepay"};
        ArrayAdapter<String> paymentAdapter = new ArrayAdapter<>(this, R.layout.row_payment, payments);
        paymentSpinner.setAdapter(paymentAdapter);

        rvSelectedSeats = findViewById(R.id.rvSelectedSeats);
        rvSelectedSeats.setAdapter(new SeatAdapter(this, selectedSeats));
        rvSelectedSeats.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        btnTransaction = findViewById(R.id.btnTransaction);

        btnTransaction.setOnClickListener(this);

        mauth = FirebaseAuth.getInstance();
        muser = mauth.getCurrentUser();

        db = FirebaseFirestore.getInstance();
    }

    // transaction headers > transaction details > update seat

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnTransaction){
            db.collection("transactionHeaders")
                    .add(new TransactionHeader(muser.getUid(), Calendar.getInstance().getTime(), Integer.parseInt(tvTotalPrice.getText().toString()), paymentSpinner.getSelectedItem().toString(), screeningTime.getTime(), selectedSeats.size(), screening.getStudio()))
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if(task.isSuccessful()){
                                DocumentReference ref = task.getResult();

                                // movie
                                ref.collection("movie")
                                        .add(screening.getMovie()).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {

                                            }
                                        });

                                // cinema
                                ref.collection("cinema")
                                        .add(screening.getCinema()).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {

                                            }
                                        });

                                // transaction details
                                for(Seat seat : selectedSeats){
                                    ref.collection("seats")
                                            .add(seat).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                                    // biar gabisa dibook lagi
                                                    db.document(seat.getRef()).update("availability", false);

                                                    Intent toSuccess = new Intent(v.getContext(), SuccessActivity.class);
                                                    // remove all activity
                                                    toSuccess.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    startActivity(toSuccess);

                                                }
                                            });
                                }
                            }
                        }
                    });
        }else if(v.getId() == R.id.btnCheckPromo){
            db.collection("promos").whereEqualTo("code", etPromo.getText().toString()).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            Promo promo = null;
                            if(task.isSuccessful()){
                                for(QueryDocumentSnapshot document : task.getResult()){
                                    promo = new Promo(document);
                                }
                                if(promo == null){
                                    Toast.makeText(OrderActivity.this, "Promo code doesn't exist", Toast.LENGTH_LONG).show();
                                }else{
                                    tvPromo.setText(String.valueOf(promo.getDiscount()) + "%");
                                    tvTotalPrice.setText(String.format("%d", (screening.getPrice() * selectedSeats.size() - (screening.getPrice() * promo.getDiscount() / 100))));
                                }
                            }
                        }
                    });
        }
    }
}