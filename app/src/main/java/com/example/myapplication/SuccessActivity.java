package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SuccessActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvToHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        tvToHome = findViewById(R.id.tvToHome);

        tvToHome.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.tvToHome){
            Intent toHome = new Intent(this, MainActivity.class);
            // remove all activity
            finishAffinity();
            startActivity(toHome);
        }
    }
}