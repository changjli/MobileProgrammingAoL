package com.example.myapplication;

import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.myapplication.adapter.ViewPagerAdapter;
import com.example.myapplication.model.Cinema;
import com.example.myapplication.model.Movie;
import com.example.myapplication.model.Screening;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ScreeningActivity extends TemplateActivity {
    TabLayout tabLayout;

    ViewPager viewPager;

    Movie movie;

    Cinema cinema;

    ArrayList<Screening> screenings;

    ArrayList<String> dates;

    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screening);

        if(getIntent().getParcelableExtra("movie") != null){
            movie = getIntent().getParcelableExtra("movie");
        }
        cinema = getIntent().getParcelableExtra("cinema");

        setupToolbar("Screenings");
        setupBackBtn();

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        dates = new ArrayList<>();

        tabLayout.setupWithViewPager(viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        addScreeningFragment(3);
        viewPager.setAdapter(viewPagerAdapter);

        for(int i=0; i<dates.size(); i++){
            tabLayout.getTabAt(i).setText(dates.get(i));
        }

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

    public void addScreeningFragment(int n){
//        Date currentDate = Calendar.getInstance().getTime();
        Date currentDate = DateFormatHelper.toDate("12 17, 2023", "MM dd, yyyy");

        for(int i=0; i<n; i++){
            String date = DateFormatHelper.toString(currentDate, "MM dd, yyyy");
            ScreeningFragment screeningFragment = new ScreeningFragment();

            Bundle args = new Bundle();
            if(movie != null){
                args.putParcelable("movie", movie);
            }
            args.putParcelable("cinema", cinema);
            args.putString("date", date);
            screeningFragment.setArguments(args);

           viewPagerAdapter.addFragment(screeningFragment, date);
           dates.add(date);

           currentDate = DateFormatHelper.addDate(currentDate, 1);
        }
    }
}