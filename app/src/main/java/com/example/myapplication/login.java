package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    TextView toRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // firebase
        mAuth = FirebaseAuth.getInstance();

        // frontend
        toRegister = findViewById(R.id.tvToRegister);

        toRegister.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
         if(mAuth.getCurrentUser() != null){
             Toast.makeText(this, "logged in", Toast.LENGTH_LONG);
         }else{
             Toast.makeText(this, "not logged in", Toast.LENGTH_LONG).show();
         }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.tvToRegister){
            Intent toRegister = new Intent(this, register.class);
            startActivity(toRegister);
        }
    }
}