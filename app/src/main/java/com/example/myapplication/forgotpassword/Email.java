package com.example.myapplication.forgotpassword;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;

public class Email extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mauth;

    CardView cvForgotPassword;

    EditText etEmail;

    Button btnSendEmail, btnCancel;

    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        mauth = FirebaseAuth.getInstance();

        etEmail = findViewById(R.id.etEmail);
        btnSendEmail = findViewById(R.id.btnSendEmail);
        btnCancel = findViewById(R.id.btnCancel);

        cvForgotPassword = findViewById(R.id.cvForgotPassword);
        cvForgotPassword.getBackground().setAlpha(230);

        btnSendEmail.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnSendEmail){
            email = etEmail.getText().toString();

            mauth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Log.d("hello", "world");
                            }else{
                                Log.d("error", "gaada emailnya");
                            }
                        }
                    });
        }else if(v.getId() == R.id.btnCancel){
            finish();
        }
    }
}