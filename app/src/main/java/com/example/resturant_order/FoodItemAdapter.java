package com.example.resturant_order;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FoodItemAdapter extends RecyclerView.Adapter<FoodItemAdapter.ViewHolder> {
    private Context context;
    private List<FoodItem> foodItemList;
    private DatabaseHelper databaseHelper;

    public FoodItemAdapter(Context context, List<FoodItem> foodItemList) {
        this.context = context;
        this.foodItemList = foodItemList;
        this.databaseHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.food_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodItem item = foodItemList.get(position);

        holder.foodName.setText(item.getName());
        holder.foodPrice.setText("$" + item.getPrice());
        holder.foodImage.setImageResource(item.getImageResId());

        holder.foodImage.setOnClickListener(v -> {
            Intent intent = new Intent(context, FoodDetailActivity.class);
            intent.putExtra("food_id", item.getId());
            intent.putExtra("food_name", item.getName());
            intent.putExtra("food_price", item.getPrice());
            intent.putExtra("food_image", item.getImageResId());
            intent.putExtra("category_id", item.getCategoryId());


            String categoryName = databaseHelper.getCategoryName(item.getCategoryId());
            intent.putExtra("category_name", categoryName);

            context.startActivity(intent);
        });

        holder.addToCartButton.setOnClickListener(v -> {
            databaseHelper.addToCart(item);
            Toast.makeText(context, item.getName() + " added to cart", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return foodItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView foodName, foodPrice;
        ImageView foodImage;
        ImageButton addToCartButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.itemName);
            foodPrice = itemView.findViewById(R.id.itemPrice);
            foodImage = itemView.findViewById(R.id.itemImage);
            addToCartButton = itemView.findViewById(R.id.addToCartButton);
        }
    }
}
