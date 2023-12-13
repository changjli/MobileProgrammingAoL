package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.model.Seat;

import java.util.ArrayList;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.SeatViewHolder> {
    Context context;

    ArrayList<Seat> seats;

    public SeatAdapter(Context context, ArrayList<Seat> seats){
        this.context = context;
        this.seats = seats;
    }

    public void setSeats(ArrayList<Seat> seats) {
        this.seats = seats;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SeatAdapter.SeatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_seat, parent, false);

        return new SeatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeatViewHolder holder, int position) {
        holder.tvSeat.setText(seats.get(position).getSeatNumber());
        if(seats.get(position).getAvailable() == false){
            holder.tvSeat.setBackgroundColor(Color.parseColor("#FF0000"));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Seat> selectedSeats = ((BookingActivity)context).getSelectedSeats();
                if(selectedSeats.contains(seats.get(holder.getAdapterPosition()))){
                    ((BookingActivity)context).removeSelectedSeats(seats.get(holder.getAdapterPosition()));
                    holder.tvSeat.setBackgroundColor(Color.parseColor("#FFFFFF"));
                }else{
                    ((BookingActivity)context).addSelectedSeats(seats.get(holder.getAdapterPosition()));
                    holder.tvSeat.setBackgroundColor(Color.parseColor("#FFFF00"));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return seats.size();
    }

    public static class SeatViewHolder extends RecyclerView.ViewHolder {

        TextView tvSeat;

        public SeatViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            tvSeat = view.findViewById(R.id.tvSeat);

        }
    }
}
