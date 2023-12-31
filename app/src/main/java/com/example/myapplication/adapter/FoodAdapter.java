package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Food;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    Context context;
    ArrayList<Food> foods;

    public FoodAdapter(Context context, ArrayList<Food> foods){
        this.context = context;
        this.foods = foods;
    }

    public void setFoods(ArrayList<Food> foods) {
        this.foods = foods;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_food, parent, false);

        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        holder.tvFoodName.setText(foods.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {

        TextView tvFoodName, tvFoodPrice, tvFoodStock, tvFoodDescription;

        public FoodViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            tvFoodName = view.findViewById(R.id.tvFoodName);

        }
    }
}
