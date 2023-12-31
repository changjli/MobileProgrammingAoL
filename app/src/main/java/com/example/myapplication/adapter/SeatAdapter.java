package com.example.myapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ChoosingSeatActivity;
import com.example.myapplication.R;
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

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull SeatViewHolder holder, int position) {
        holder.tvSeat.setText(seats.get(position).getSeatNumber());

        if(seats.get(position).getSeatNumber().contains("2") || seats.get(position).getSeatNumber().contains("5")){

        }

        if(seats.get(position).getAvailability() == false){
            holder.tvSeat.setBackgroundColor(Color.parseColor("#ff4949"));
        }else{
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View v) {
                    ArrayList<Seat> selectedSeats = ((ChoosingSeatActivity)context).getSelectedSeats();
                    if(selectedSeats.contains(seats.get(holder.getAdapterPosition()))){
                        ((ChoosingSeatActivity)context).removeSelectedSeats(seats.get(holder.getAdapterPosition()));
                        holder.tvSeat.setBackgroundColor(Color.parseColor("#848484"));
                    }else{
                        ((ChoosingSeatActivity)context).addSelectedSeats(seats.get(holder.getAdapterPosition()));
                        holder.tvSeat.setBackgroundColor(Color.parseColor("#148DFF"));
                    }
                }
            });
        }
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
