package com.example.resturant_order;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

class FoodCategoryAdapter extends RecyclerView.Adapter<FoodCategoryAdapter.ViewHolder> {
    private Context context;
    private List<FoodCategory> foodCategories;
    private OnCategoryClickListener clickListener;

    public interface OnCategoryClickListener {
        void onCategoryClick(FoodCategory category);
    }

    public FoodCategoryAdapter(Context context, List<FoodCategory> cuisines, OnCategoryClickListener listener) {
        this.context = context;
        this.foodCategories = cuisines;
        this.clickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodCategory category = foodCategories.get(position);
        holder.categoryName.setText(category.getName());
        holder.itemsCount.setText(category.getPlaces() + " items");

            holder.categoryImage.setImageResource(category.getImageResId());

        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onCategoryClick(category);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodCategories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView categoryImage;
        TextView categoryName, itemsCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImage = itemView.findViewById(R.id.categoryImage);
            categoryName = itemView.findViewById(R.id.categoryName);
            itemsCount = itemView.findViewById(R.id.categoryItemsCount);
        }
    }
}
