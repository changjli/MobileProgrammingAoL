package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Profile extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    FirebaseAuth mauth;

    FirebaseUser muser;

    FirebaseFirestore db;

    User user;

    Toolbar myToolbar;

    TextView tvHello;

    EditText etEmail, etName, etAddress, etDob;

    Button btnDatePicker, btnSave, btnLogout;

    DatePickerDialog datePicker;

    RadioButton genderMale, genderFemale, genderUnsure;

    SimpleDateFormat df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // auth
        mauth = FirebaseAuth.getInstance();
        muser = mauth.getCurrentUser();

        // firestore
        db = FirebaseFirestore.getInstance();

        // toolbar
        myToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // widget
        tvHello = findViewById(R.id.tvHello);

        etEmail = findViewById(R.id.etEmail);
        etName = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);
        etDob = findViewById(R.id.etDob);

        btnDatePicker = findViewById(R.id.btnDatePicker);
        btnSave = findViewById(R.id.btnSave);
        btnLogout = findViewById(R.id.btnLogout);

        genderMale = findViewById(R.id.genderMale);
        genderFemale = findViewById(R.id.genderFemale);
        genderUnsure = findViewById(R.id.genderUnsure);

        btnDatePicker.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnLogout.setOnClickListener(this);

        genderMale.setOnCheckedChangeListener(this);
        genderFemale.setOnCheckedChangeListener(this);
        genderUnsure.setOnCheckedChangeListener(this);

        df = new SimpleDateFormat("MM dd, yyyy");
    }

    @Override
    protected void onStart() {
        super.onStart();

        try {
            // read data
            db.collection("users")
                    .whereEqualTo("uid", mauth.getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                if(!task.getResult().isEmpty()){
                                    for (QueryDocumentSnapshot document : task.getResult()){
                                        user = new User(document);

                                        etName.setText(user.getName());
                                        etAddress.setText(user.getAddress());
                                        etDob.setText(df.format(user.getDob()));
                                        Log.d("gender", user.getGender());
                                        if(user.getGender().equals("male")){
                                            genderMale.setChecked(true);
                                        }else if(user.getGender().equals("female")){
                                            genderFemale.setChecked(true);
                                        }else if(user.getGender().equals("unsure")){
                                            genderUnsure.setChecked(true);
                                        }

                                    }
                                }else{
                                    user = new User();
                                }
                                etEmail.setText(muser.getEmail());
                            }else{
                                Toast.makeText(Profile.this, "Profile read error", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }catch(Exception e){
            Log.d("merror", e.getMessage());
        }

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnDatePicker){
            showDateDialog();
        }else if(v.getId() == R.id.btnSave){
            user.setUid(muser.getUid());
            user.setEmail(muser.getEmail());
            user.setName(etName.getText().toString());
            user.setAddress(etAddress.getText().toString());
            try {
                user.setDob(df.parse(etDob.getText().toString()));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            if(user.getId() == null){
                // add data if user doesnt exist
                db.collection("users")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(Profile.this, "Profile update success", Toast.LENGTH_LONG);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Profile.this, "Profile update error",Toast.LENGTH_LONG );
                            }
                        });
            }else{
                // update data if user already exist
                db.collection("users")
                        .document(user.getId())
                        .set(user)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(Profile.this, "Profile update success", Toast.LENGTH_LONG);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Profile.this, "Profile update error",Toast.LENGTH_LONG );
                            }
                        });
            }
        }else if(v.getId() == R.id.btnLogout){
            mauth.signOut();
            Intent toLogin = new Intent(this, Login.class);
            startActivity(toLogin);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDateDialog(){
        Calendar initialDate = Calendar.getInstance();

        if(user.getDob() != null){
            initialDate.setTime(user.getDob());
        }

        datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, monthOfYear, dayOfMonth);

                etDob.setText(df.format(selectedDate.getTime()));
            }

        },initialDate.get(Calendar.YEAR), initialDate.get(Calendar.MONTH), initialDate.get(Calendar.DAY_OF_MONTH));

        datePicker.show();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(genderMale.isChecked()){
            user.setGender("male");
        }else if(genderFemale.isChecked()){
            user.setGender(genderFemale.getText().toString());
        }else if(genderUnsure.isChecked()){
            user.setGender(genderUnsure.getText().toString());
        }
    }
}