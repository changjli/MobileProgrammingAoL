package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.model.Ticket;
import com.example.myapplication.model.Transaction;

import java.util.ArrayList;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {

    Context context;
    Transaction transaction;

    public TicketAdapter(Context context, Transaction transaction){
        this.context = context;
        this.transaction = transaction;
    }

    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_ticket, parent, false);

        return new TicketViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        Glide
                .with(holder.itemView)
                .load(transaction.getMovie().getImageUrl())
                .centerCrop()
                .into(holder.ivMovieImage);

        holder.tvScreeningDate.setText(DateFormatHelper.toString(transaction.getScreeningDateTime(), "dd-MM"));
        holder.tvScreeningTime.setText(DateFormatHelper.toString(transaction.getScreeningDateTime(), "HH:mm"));
        holder.tvSeatNumber.setText(transaction.getSeats().get(position).getSeatNumber());
        holder.tvCinemaStudio.setText(String.valueOf(transaction.getStudio()));
    }

    @Override
    public int getItemCount() {
        return transaction.getSeats().size();
    }

    public static class TicketViewHolder extends RecyclerView.ViewHolder{

        ImageView ivMovieImage;

        TextView tvScreeningDate, tvScreeningTime, tvSeatNumber, tvCinemaStudio;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);

            ivMovieImage = itemView.findViewById(R.id.ivMovieImage);
            tvScreeningDate = itemView.findViewById(R.id.tvScreeningDate);
            tvScreeningTime = itemView.findViewById(R.id.tvScreeningTime);
            tvSeatNumber = itemView.findViewById(R.id.tvSeatNumber);
            tvCinemaStudio = itemView.findViewById(R.id.tvCinemaStudio);
        }
    }
}
