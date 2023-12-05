//package com.example.myapplication;
//
//import android.app.DatePickerDialog;
//import android.app.Dialog;
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.DialogFragment;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.DatePicker;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//import androidx.appcompat.widget.Toolbar;
//
//import com.example.myapplication.model.User;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.FirebaseFirestore;
//
//import org.w3c.dom.Text;
//
//import java.sql.Time;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Calendar;
//import java.util.Date;
//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link AccountFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class AccountFragment extends Fragment implements View.OnClickListener {
//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public AccountFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment AccountFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static AccountFragment newInstance(String param1, String param2) {
//        AccountFragment fragment = new AccountFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    TextView tvHello, tvDatePicker;
//
//    EditText etEmail, etName, etAddress;
//
//    Button btnDatePicker, btnSave;
//
//    DatePickerDialog datePicker;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_account, container, false);
//
//        // toolbar
//        Toolbar myToolbar = getActivity().findViewById(R.id.myToolbar);
//        ((AppCompatActivity)getActivity()).setSupportActionBar(myToolbar);
//
//        tvHello = view.findViewById(R.id.tvHello);
//        tvDatePicker = view.findViewById(R.id.tvDatePicker);
//
//        etEmail = view.findViewById(R.id.etEmail);
//        etName = view.findViewById(R.id.etName);
//        etAddress = view.findViewById(R.id.etAddress);
//
//        btnDatePicker = view.findViewById(R.id.btnDatePicker);
//        btnSave = view.findViewById(R.id.btnSave);
//
//        btnDatePicker.setOnClickListener(this);
//        btnSave.setOnClickListener(this);
//
//        if(getActivity().getSupportFragmentManager().getBackStackEntryCount() > 0){
//            // back button
//            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }
//
//        // Inflate the layout for this fragment
//        return view;
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if(user != null){
//            etEmail.setText(user.getEmail());
//            etName.setText(user.getDisplayName());
//        }
//    }
//
//    @Override
//    public void onClick(View v) {
//        if(v.getId() == R.id.btnDatePicker){
////            DatePickerFragment datePickerFragment = new DatePickerFragment();
////            datePickerFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
//            showDateDialog();
//        }else if(v.getId() == R.id.btnSave){
//            Log.d("helo", "world");
//            FirebaseFirestore db = FirebaseFirestore.getInstance();
//            User user = new User("3", "4", "5", "6", "7", "8", "9");
//            db.collection("users").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                        @Override
//                        public void onSuccess(DocumentReference documentReference) {
//                            Log.d("HELLO", "DocumentSnapshot added with ID: " + documentReference.getId());
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.w("HELLO", "Error adding document", e);
//                        }
//                    });
//        }
//    }
//
//
//    private void showDateDialog(){
//
//        /**
//         * Calendar untuk mendapatkan tanggal sekarang
//         */
//        Calendar newCalendar = Calendar.getInstance();
//
//        /**
//         * Initiate DatePicker dialog
//         */
//        datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
//
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//
//                /**
//                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
//                 */
//
//                /**
//                 * Set Calendar untuk menampung tanggal yang dipilih
//                 */
//                Calendar newDate = Calendar.getInstance();
//                newDate.set(year, monthOfYear, dayOfMonth);
//
//                /**
//                 * Update TextView dengan tanggal yang kita pilih
//                 */
//                tvDatePicker.setText("Tanggal dipilih : ");
//            }
//
//        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
//
//        /**
//         * Tampilkan DatePicker dialog
//         */
//        datePicker.show();
//    }
//
//
////    public static class DatePickerFragment extends DialogFragment
////            implements DatePickerDialog.OnDateSetListener {
////
////        @Override
////        public Dialog onCreateDialog(Bundle savedInstanceState) {
////            // Use the current date as the default date in the picker.
////            final Calendar c = Calendar.getInstance();
////            int year = c.get(Calendar.YEAR);
////            int month = c.get(Calendar.MONTH);
////            int day = c.get(Calendar.DAY_OF_MONTH);
////
////            // Create a new instance of DatePickerDialog and return it.
////            return new DatePickerDialog(requireContext(), this, year, month, day);
////        }
////
////        public void onDateSet(DatePicker view, int year, int month, int day) {
////            // Do something with the date the user picks.
////            Toast.makeText(this, "hello world", Toast.LENGTH_SHORT).show();
////        }
////    }
//}