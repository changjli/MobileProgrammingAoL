package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ChoosingSeatActivity;
import com.example.myapplication.DateFormatHelper;
import com.example.myapplication.R;
import com.example.myapplication.model.Screening;
import com.example.myapplication.model.ScreeningTime;

import java.util.ArrayList;

public class ScreeningTimeAdapter extends RecyclerView.Adapter<ScreeningTimeAdapter.TimeViewHolder> {
    Context context;
    Screening screening;
    ArrayList<ScreeningTime> screeningTimes;

    public ScreeningTimeAdapter(Context context, Screening screening){
        this.context = context;
        this.screening = screening;
        this.screeningTimes = screening.getTime();
    }

    @NonNull
    @Override
    public TimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_screening_time, parent, false);

        return new TimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeViewHolder holder, int position) {
        holder.tvTime.setText(DateFormatHelper.toString(screeningTimes.get(position).getTime(), "HH:mm"));
        holder.tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toBook = new Intent(v.getContext(), ChoosingSeatActivity.class);
                toBook.putExtra("screeningTime", screeningTimes.get(holder.getAdapterPosition()));
                toBook.putExtra("screening", screening);
//                toBook.putExtra("movie", movie);
//                toBook.putExtra("cinema", cinema);
                v.getContext().startActivity(toBook);
            }
        });
    }


    @Override
    public int getItemCount() {
        return screeningTimes.size();
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
