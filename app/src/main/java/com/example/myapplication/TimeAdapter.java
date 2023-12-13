package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.model.Cinema;
import com.example.myapplication.model.Movie;
import com.example.myapplication.model.ScreeningTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.TimeViewHolder> {
    Context context;
    ArrayList<ScreeningTime> time;

    Movie movie;

    Cinema cinema;

    public TimeAdapter(Context context, ArrayList<ScreeningTime> time, Movie movie, Cinema cinema){
        this.context = context;
        this.time = time;
        this.movie = movie;
        this.cinema = cinema;
    }

    @NonNull
    @Override
    public TimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_time, parent, false);

        return new TimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeViewHolder holder, int position) {
        holder.tvTime.setText(DateFormatHelper.toString(time.get(position).getTime(), "dd-MM-YYYY"));
        holder.tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toBook = new Intent(v.getContext(), BookingActivity.class);
                toBook.putExtra("screeningTime", time.get(holder.getAdapterPosition()));
                Log.v("time", DateFormatHelper.toString(time.get(holder.getAdapterPosition()).getTime(), "dd-MM-YYYY"));
                toBook.putExtra("cape", "hello");
                toBook.putExtra("movie", movie);
                toBook.putExtra("cinema", cinema);
                v.getContext().startActivity(toBook);
            }
        });
    }


    @Override
    public int getItemCount() {
        return time.size();
    }

    public static class TimeViewHolder extends RecyclerView.ViewHolder {

        TextView tvTime;

        public TimeViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            tvTime = view.findViewById(R.id.tvtime);

        }
    }
}
