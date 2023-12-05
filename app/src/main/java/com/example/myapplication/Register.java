package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    EditText etEmail, etPassword;

    Button btnRegister;

    TextView tvToLogin;

    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvToLogin = findViewById(R.id.tvToLogin);

        btnRegister.setOnClickListener(this);
        tvToLogin.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() != null){
            Intent toHome = new Intent(this, Home.class);
            startActivity(toHome);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnRegister){
            email = etEmail.getText().toString();
            password = etPassword.getText().toString();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent toHome = new Intent(Register.this, MainActivity.class);
                                startActivity(toHome);
                            }else{
                                Toast.makeText(Register.this, "error", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }else if(v.getId() == R.id.tvToLogin){
            Intent toLogin = new Intent(this, Login.class);
            startActivity(toLogin);
        }
    }
}