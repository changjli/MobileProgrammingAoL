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

import com.example.myapplication.forgotpassword.Email;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    EditText etEmail, etPassword;

    Button btnLogin;

    TextView tvToRegister, tvToForgotPassword;

    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // firebase
        mAuth = FirebaseAuth.getInstance();

        // frontend
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvToRegister = findViewById(R.id.tvToRegister);
        tvToForgotPassword = findViewById(R.id.tvToForgotPassword);

        btnLogin.setOnClickListener(this);
        tvToRegister.setOnClickListener(this);
        tvToForgotPassword.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
         if(currentUser != null){
             Intent toHome = new Intent(this, MainActivity.class);
             startActivity(toHome);
         }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnLogin){
            email = etEmail.getText().toString();
            password = etPassword.getText().toString();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent toHome = new Intent(Login.this, MainActivity.class);
                                startActivity(toHome);
                            }else{
                                Toast.makeText(Login.this, "login error", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        } else if(v.getId() == R.id.tvToRegister){
            Intent toRegister = new Intent(this, Register.class);
            startActivity(toRegister);
        } else if(v.getId() == R.id.tvToForgotPassword){
            Intent toForgotPassword = new Intent(this, Email.class);
            startActivity(toForgotPassword);
        }
    }
}