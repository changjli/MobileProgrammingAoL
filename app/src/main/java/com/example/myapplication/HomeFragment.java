package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.adapter.MovieAdapter;
import com.example.myapplication.model.Movie;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

      return inflater.inflate(R.layout.fragment_home, container, false);
    }

    // dari sini kebawah seharusnya mirip kaya activity
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // carousel
        List<String> carouselItems = new ArrayList<>();
        carouselItems.add("_61798ddc3a43c56b8617e176e88198f");
        carouselItems.add("_df1593f4690c96aa7c28cb08ea10e6c");
        carouselItems.add("b90cb5d0d303e7ac4ff60e482a5f913b");

        SliderView carousel = getActivity().findViewById(R.id.carouselSlider);
        carousel.setSliderAdapter(new CarouselAdapter(getContext(), carouselItems));
        // end

        // rv movies
        ArrayList<Movie> movies = new ArrayList<>();

        RecyclerView rvMovies = getActivity().findViewById(R.id.rvMovies);
        MovieAdapter movieAdatper = new MovieAdapter(getContext(), movies);
        rvMovies.setAdapter(movieAdatper);
        rvMovies.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvMovies.addItemDecoration(new GridSpaceItemDecoration(2, 30, false));
        // end

        // fetch movies
        db.collection("movies")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document.exists()){
                                    movies.add(new Movie(document));
                                }
                            }
                            movieAdatper.setMovies(movies);
                        } else {
                            Toast.makeText(getContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

//        movies.add(new Movie("1", "2", 120, temp, temp, "4", "5", "6", "7", "8", "9", "10", "11", 4.0));
//        movies.add(new Movie("1", "2", 120, temp, temp, "4", "5", "6", "7", "8", "9", "10", "11", 4.0));
//        movies.add(new Movie("1", "2", 120, temp, temp, "4", "5", "6", "7", "8", "9", "10", "11", 4.0));
//        movies.add(new Movie("1", "2", 120, temp, temp, "4", "5", "6", "7", "8", "9", "10", "11", 4.0));
//        movies.add(new Movie("1", "2", 120, temp, temp, "4", "5", "6", "7", "8", "9", "10", "11", 4.0));
//        movies.add(new Movie("1", "2", 120, temp, temp, "4", "5", "6", "7", "8", "9", "10", "11", 4.0));



    }
}