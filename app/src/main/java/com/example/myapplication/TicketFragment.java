package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.myapplication.model.Cinema;
import com.example.myapplication.model.Movie;
import com.example.myapplication.model.ScreeningTime;
import com.example.myapplication.model.Seat;
import com.example.myapplication.model.Transaction;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TicketFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TicketFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TicketFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TicketFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TicketFragment newInstance(String param1, String param2) {
        TicketFragment fragment = new TicketFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    RecyclerView rvTransactions, rvTickets;
    ImageView ivTicketEmpty, ivHistoryEmpty;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ticket, container, false);
    }

    /**
     * Called immediately after {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     * has returned, but before any saved state has been restored in to the view.
     * This gives subclasses a chance to initialize themselves once
     * they know their view hierarchy has been completely created.  The fragment's
     * view hierarchy is not however attached to its parent at this point.
     *
     * @param view               The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ivTicketEmpty = view.findViewById(R.id.ivTicketEmpty);
        ivHistoryEmpty = view.findViewById(R.id.ivHistoryEmpty);

        ArrayList<Transaction> tickets = new ArrayList<>();
        ArrayList<Transaction> histories = new ArrayList<>();

        rvTickets = view.findViewById(R.id.rvTickets);
        TransactionAdapter ticketAdapter = new TransactionAdapter(getContext(), tickets);
        rvTickets.setAdapter(ticketAdapter);
        rvTickets.setLayoutManager(new LinearLayoutManager(getContext()));

        rvTransactions = view.findViewById(R.id.rvTransactions);
        TransactionAdapter transactionAdapter = new TransactionAdapter(getContext(), histories);
        rvTransactions.setAdapter(transactionAdapter);
        rvTransactions.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mauth = FirebaseAuth.getInstance();
        FirebaseUser muser = mauth.getCurrentUser();

        db.collection("transactionHeaders")
                .whereEqualTo("uid", muser.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                Transaction transaction = new Transaction(document);

                                transaction.getRef().collection("movie").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful()){
                                            for(QueryDocumentSnapshot document : task.getResult()){
                                                transaction.setMovie(new Movie(document));
                                            }

                                            transaction.getRef().collection("cinema").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if(task.isSuccessful()){
                                                        for(QueryDocumentSnapshot document : task.getResult()){
                                                            transaction.setCinema(new Cinema(document));
                                                        }

                                                        transaction.getRef().collection("seats").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                if(task.isSuccessful()){
                                                                    for(QueryDocumentSnapshot document : task.getResult()){
                                                                        if(document.exists()){
                                                                            transaction.addSeat(new Seat(document));
                                                                        }
                                                                    }

                                                                    if(DateFormatHelper.getCurrentDateTime().compareTo(transaction.getScreeningDateTime()) < 0){
                                                                        tickets.add(transaction);
                                                                        if(ivTicketEmpty.getVisibility() == View.VISIBLE){
                                                                            ivTicketEmpty.setVisibility(View.GONE);
                                                                        }
                                                                    }else{
                                                                        histories.add(transaction);
                                                                        if(ivHistoryEmpty.getVisibility() == View.VISIBLE){
                                                                            ivHistoryEmpty.setVisibility(View.GONE);
                                                                        }
                                                                    }

                                                                    ticketAdapter.setTransactions(tickets);
                                                                    transactionAdapter.setTransactions(histories);

                                                                }
                                                            }
                                                        });
                                                    }
                                                }
                                            });

                                        }
                                    }
                                });

//                                transaction.getScreening().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                        if(task.isSuccessful()){
//                                            DocumentSnapshot document = task.getResult();
//                                            if(document.exists()){
//                                                ScreeningTime screeningTime = new ScreeningTime(document);
//
//                                                if(DateFormatHelper.getCurrentDateTime().compareTo(screeningTime.getTime()) > 0){
//                                                    tickets.add(transaction);
//                                                }else{
//                                                    histories.add(transaction);
//                                                }
//
//                                                ticketAdapter.setTransactions(tickets);
//                                                transactionAdapter.setTransactions(histories);
//
//                                            }
//                                        }
//                                    }
//                                });
                            }
                        }
                    }
                });
    }
}