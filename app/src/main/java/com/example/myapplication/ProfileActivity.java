package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.model.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends TemplateActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    FirebaseUser muser;

    User user;

    CircleImageView cvProfilePicture;

    TextView tvHello;

    EditText etEmail, etName, etAddress, etDob;

    Button btnSave, btnLogout;

    ImageView btnDatePicker;

    DatePickerDialog datePicker;

    RadioButton genderMale, genderFemale, genderUnsure;

    SimpleDateFormat df;

    private int PICK_IMAGE_REQUEST = 22;

    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setupToolbar("Profile");
        setupBackBtn();

        // auth
        muser = mauth.getCurrentUser();

        // widget
        cvProfilePicture = findViewById(R.id.cvProfilePicture);

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

        cvProfilePicture.setOnClickListener(this);

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
                                        if(user.getGender().equals("male")){
                                            genderMale.setChecked(true);
                                        }else if(user.getGender().equals("female")){
                                            genderFemale.setChecked(true);
                                        }else if(user.getGender().equals("unsure")){
                                            genderUnsure.setChecked(true);
                                        }
                                        if(user.getProfileImage() != null){
                                            Log.v("img", user.getProfileImage());
                                            Glide
                                                    .with(ProfileActivity.this)
                                                    .load(user.getProfileImage())
                                                    .centerCrop()
                                                    .into(cvProfilePicture);
                                        }

                                    }
                                }else{
                                    user = new User();
                                }
                                etEmail.setText(muser.getEmail());
                            }else{
                                Toast.makeText(ProfileActivity.this, "Profile read error", Toast.LENGTH_LONG).show();
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
            String name = etName.getText().toString();
            String address = etAddress.getText().toString();
            String dob = etDob.getText().toString();

            if(name.equals("") && address.equals("") && dob.equals("")){
                Toast.makeText(this, "all fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            user.setUid(muser.getUid());
            user.setEmail(muser.getEmail());
            user.setName(name);
            user.setAddress(address);
            try {
                user.setDob(df.parse(dob));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            if(filePath != null){
                // delete image lama
                deleteImage();

                // upload image baru + update profile
                uploadImage();
            }else{
                updateProfile();
            }


        }else if(v.getId() == R.id.btnLogout){
            mauth.signOut();
            Intent toLogin = new Intent(this, MainActivity.class);
            finishAffinity();
            startActivity(toLogin);
        }else if(v.getId() == R.id.cvProfilePicture){
            selectImage();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                cvProfilePicture.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    // show select image prompt
    private  void selectImage(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void uploadImage(){
        if (filePath != null) {

            FirebaseStorage storage = FirebaseStorage.getInstance();

            StorageReference storageRef = storage.getReference();

            // generate random id
            storageRef = storageRef.child("images/" + UUID.randomUUID().toString());

            // Get the data from an ImageView as bytes
            cvProfilePicture.setDrawingCacheEnabled(true);
            cvProfilePicture.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) cvProfilePicture.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            // failure/success > continue > complete
            StorageReference finalStorageRef = storageRef;
            storageRef.putBytes(data).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.v("error file upload", exception.getMessage());
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                   Log.v("success", "file upload");
                }
            }).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return finalStorageRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        Log.v("uri", downloadUri.toString());
                        user.setProfileImage(downloadUri.toString());
                        updateProfile();
                    } else {
                        // Handle failures
                        // ...
                    }
                }

            });
        }
    }

    private void updateProfile(){
        if(user.getId() == null){
            // add data if user doesnt exist
            db.collection("users")
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(ProfileActivity.this, "Profile update success", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ProfileActivity.this, "Profile update error",Toast.LENGTH_LONG ).show();
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
                            Toast.makeText(ProfileActivity.this, "Profile update success", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ProfileActivity.this, "Profile update error",Toast.LENGTH_LONG ).show();
                        }
                    });
        }
    }

    private void deleteImage(){
        if(user.getProfileImage() != null && !user.getProfileImage().equals("") && filePath != null){
            FirebaseStorage storage = FirebaseStorage.getInstance();

            StorageReference storageRef = storage.getReference();

            // sebenernya bisa juga save string path sebagai tambahan
            storageRef = storage.getReferenceFromUrl(user.getProfileImage());

            // gausah ditungguin soalnya ga ngaruh ke database
            storageRef.delete();
        }

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