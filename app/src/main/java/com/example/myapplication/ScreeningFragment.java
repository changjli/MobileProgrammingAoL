package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.adapter.ScreeningAdapter;
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
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScreeningFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScreeningFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ScreeningFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScreeningFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScreeningFragment newInstance(String param1, String param2) {
        ScreeningFragment fragment = new ScreeningFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_screening, container, false);
    }

    Movie movie;
    Cinema cinema;
    Date date, date2;
    ArrayList<Screening> screenings;

    TextView tvCinemaPreciseLocation, tvCinemaPhoneNumber;
    RecyclerView rvScreenings;
    ScreeningAdapter screeningAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        if(args.getParcelable("movie") != null){
            movie = args.getParcelable("movie");
        }
        cinema = args.getParcelable("cinema");
        date = DateFormatHelper.toDate(args.getString("date"), "MM dd, yyyy");
        date2 = DateFormatHelper.addDate(date, 1);

        tvCinemaPreciseLocation = view.findViewById(R.id.tvCinemaPreciseLocation);
        tvCinemaPhoneNumber = view.findViewById(R.id.tvCinemaPhoneNumber);
        tvCinemaPreciseLocation.setText(cinema.getPreciseLocation());
        tvCinemaPhoneNumber.setText(cinema.getPhoneNumber());

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        screenings = new ArrayList<>();

        rvScreenings = view.findViewById(R.id.rvScreenings);
        screeningAdapter = new ScreeningAdapter(getContext(), screenings);
        rvScreenings.setAdapter(screeningAdapter);
        rvScreenings.setLayoutManager(new LinearLayoutManager(getContext()));

        if(movie != null){
            db.collection("screenings")
                    .whereEqualTo("movie", movie.getRef())
                    .whereEqualTo("cinema", cinema.getRef())
                    .whereGreaterThanOrEqualTo("date", date)
                    .whereLessThan("date", date2)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Screening screening = new Screening(document);
                                    screening.setMovie(movie);
                                    screening.setCinema(cinema);

                                    document.getReference().collection("time")
                                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if(task.isSuccessful()){
                                                        for(QueryDocumentSnapshot document : task.getResult()){
                                                            screening.addTime(document);
                                                        }
                                                        screenings.add(screening);
                                                        screeningAdapter.setScreenings(screenings);
                                                    }
                                                }
                                            });

                                }
                            } else {

                            }
                        }
                    });
        }else {
            db.collection("screenings")
                    .whereEqualTo("cinema", cinema.getRef())
                    .whereGreaterThanOrEqualTo("date", date)
                    .whereLessThan("date", date2)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Screening screening = new Screening(document);
                                    screening.setCinema(cinema);

                                    document.getDocumentReference("movie")
                                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if(task.isSuccessful()){
                                                        DocumentSnapshot document = task.getResult();
                                                        screening.setMovie(new Movie(document));
                                                    }
                                                }
                                            });

                                    document.getReference().collection("time")
                                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if(task.isSuccessful()){
                                                        for(QueryDocumentSnapshot document : task.getResult()){
                                                            screening.addTime(document);
                                                        }
                                                        screenings.add(screening);
                                                        screeningAdapter.setScreenings(screenings);
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
}