package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.model.Transaction;

public class TransactionActivity extends TemplateActivity {

    Transaction transaction;

    RecyclerView rvTickets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        setupToolbar("Your Ticket");
        setupBackBtn();

        transaction = getIntent().getExtras().getParcelable("transaction");

        rvTickets = findViewById(R.id.rvTickets);
        rvTickets.setAdapter(new TicketAdapter(this, transaction));
        rvTickets.setLayoutManager(new LinearLayoutManager(this));
    }
}