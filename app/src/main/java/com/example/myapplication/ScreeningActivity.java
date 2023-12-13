package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.example.myapplication.model.Cinema;
import com.example.myapplication.model.Movie;
import com.example.myapplication.model.Screening;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ScreeningActivity extends AppCompatActivity {

    Toolbar myToolbar;

    TabLayout tabLayout;

    ViewPager viewPager;

    Movie movie;
    Cinema cinema;

    ArrayList<Screening> screenings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screening);

        if(getIntent().getParcelableExtra("movie") != null){
            movie = getIntent().getParcelableExtra("movie");
        }

        cinema = getIntent().getParcelableExtra("cinema");

        myToolbar = findViewById(R.id.myToolbar);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        setSupportActionBar(myToolbar);

        Date currentTime = Calendar.getInstance().getTime();

        ScreeningFragment screeningFragment = new ScreeningFragment();
        Bundle args = new Bundle();
        if(movie != null){
            args.putParcelable("movie", movie);
        }
        args.putParcelable("cinema", cinema);
        args.putString("date", DateFormatHelper.toString(currentTime, "MM dd, yyyy"));
        screeningFragment.setArguments(args);

        ScreeningFragment screeningFragment1 = new ScreeningFragment();
        Bundle args2 = new Bundle();
        if(movie != null){
            args2.putParcelable("movie", movie);
        }
        args2.putParcelable("cinema", cinema);
        args2.putString("date", DateFormatHelper.toString(DateFormatHelper.addDate(currentTime, 1), "MM dd, yyyy"));
        screeningFragment1.setArguments(args2);

        ScreeningFragment screeningFragment2 = new ScreeningFragment();
        Bundle args3 = new Bundle();
        if(movie != null){
            args3.putParcelable("movie", movie);
        }
        args3.putParcelable("cinema", cinema);
        args3.putString("date", DateFormatHelper.toString(DateFormatHelper.addDate(currentTime, 1), "MM dd, yyyy"));
        args3.putString("date", DateFormatHelper.toString(DateFormatHelper.addDate(currentTime, 2), "MM dd, yyyy"));
        screeningFragment2.setArguments(args3);

        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFragment(screeningFragment, "");
        viewPagerAdapter.addFragment(screeningFragment1, "");
        viewPagerAdapter.addFragment(screeningFragment2, "");

        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.getTabAt(0).setText("1");
        tabLayout.getTabAt(1).setText("2");
        tabLayout.getTabAt(2).setText("3");

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        screenings = new ArrayList<>();





    }
}