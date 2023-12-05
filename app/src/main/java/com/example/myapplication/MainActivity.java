package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    Toolbar myToolbar;

    FrameLayout flContainer;

    NavigationBarView bottomNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        // first page
        injectFragment(new HomeFragment(), "Home");

        flContainer = findViewById(R.id.flContainer);
        bottomNavigationBar = findViewById(R.id.bottomNavigationBar);
        bottomNavigationBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                String tag = "";
                if(item.getItemId() == R.id.toHome){
                    fragment = new HomeFragment();
                    tag = "Home";
                }else if(item.getItemId() == R.id.toCinema){
                    fragment = new CinemaFragment();
                    tag = "Cinema";
                }else if(item.getItemId() == R.id.toTicket){
                    fragment = new TicketFragment();
                    tag = "Ticket";
                }
                return injectFragment(fragment, tag);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Intent toLogin = new Intent(this, Login.class);
            startActivity(toLogin);
        }
    }

    private boolean injectFragment(Fragment fragment, String tag) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flContainer, fragment)
                    .addToBackStack(tag)
                    .commit();
            return true;
        }
        return false;
    }

    // option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection.
       if(item.getItemId() == R.id.toProfile) {
           Intent toProfile = new Intent(this, Profile.class);
           startActivity(toProfile);
       }
       return super.onOptionsItemSelected(item);
    }
}