package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

class CarouselAdapter extends
        SliderViewAdapter<CarouselAdapter.SliderAdapterVH> {

    private Context context;
    private List<String> mSliderItems = new ArrayList<>();

    public CarouselAdapter(Context context, List<String> mSliderItems) {
        this.context = context;
        this.mSliderItems = mSliderItems;
    }

    public void renewItems(List<String> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mSliderItems.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(String sliderItem) {
        this.mSliderItems.add(sliderItem);
        notifyDataSetChanged();
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.carousel_item, null);
        return new SliderAdapterVH(inflate);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        String sliderItem = mSliderItems.get(position);

        int id = context.getResources().getIdentifier(sliderItem, "drawable", context.getPackageName());
        viewHolder.ivCarousel.setImageResource(id);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "This is item in position " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mSliderItems.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView ivCarousel;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            ivCarousel = itemView.findViewById(R.id.ivCarousel);
            this.itemView = itemView;
        }
    }

}