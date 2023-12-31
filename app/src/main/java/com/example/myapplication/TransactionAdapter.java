package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.model.Transaction;

import java.util.ArrayList;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    Context context;

    ArrayList<Transaction> transactions;

    public TransactionAdapter(Context context, ArrayList<Transaction> transactions){
        this.context = context;
        this.transactions = transactions;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_transaction, parent, false);

        return new TransactionViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Glide
                .with(holder.itemView)
                .load(transactions.get(position).getMovie().getImageUrl())
                .centerCrop()
                .into(holder.ivMovieImage);

        holder.tvMovieTitle.setText(transactions.get(position).getMovie().getTitle());
        holder.tvCinemaLocation.setText(transactions.get(position).getCinema().getLocation());
        holder.tvSeatQty.setText(String.valueOf(transactions.get(position).getSeatQty()));
        holder.tvScreeningDateTime.setText(DateFormatHelper.toString(transactions.get(position).getScreeningDateTime(), "EEEE, dd-MM-yyyy HH:mm"));

        if(DateFormatHelper.getCurrentDateTime().compareTo(transactions.get(position).getScreeningDateTime()) < 0){
            holder.tvTicketStatus.setBackgroundColor(Color.parseColor("#12B772"));
            holder.tvTicketStatus.setText("active");
        }else{
            holder.tvTicketStatus.setBackgroundColor(Color.parseColor("#ff4949"));
            holder.tvTicketStatus.setText("expired");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toTransaction = new Intent(context, TransactionActivity.class);
                toTransaction.putExtra("transaction", transactions.get(holder.getAdapterPosition()));
                context.startActivity(toTransaction);
            }
        });
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder{

        ImageView ivMovieImage;
        TextView tvMovieTitle, tvCinemaLocation, tvSeatQty, tvScreeningDateTime, tvTicketStatus;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);

           ivMovieImage = itemView.findViewById(R.id.ivMovieImage);
           tvMovieTitle = itemView.findViewById(R.id.tvMovieTitle);
           tvCinemaLocation = itemView.findViewById(R.id.tvCinemaLocation);
           tvSeatQty = itemView.findViewById(R.id.tvSeatQty);
           tvScreeningDateTime = itemView.findViewById(R.id.tvScreeningDateTime);
           tvTicketStatus = itemView.findViewById(R.id.tvTicketStatus);
        }
    }
}
