package com.example.resturant_order;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class FoodDetailActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        dbHelper = new DatabaseHelper(this);


        ImageView foodImage = findViewById(R.id.foodDetailImage);
        TextView foodName = findViewById(R.id.foodDetailName);
        TextView foodCategory = findViewById(R.id.foodDetailCategory);
        TextView foodPrice = findViewById(R.id.foodDetailPrice);
        Button addToCartButton = findViewById(R.id.addToCartButton);
        ImageButton backButton = findViewById(R.id.backButton);

        int foodId = getIntent().getIntExtra("food_id", -1);
        String name = getIntent().getStringExtra("food_name");
        double price = getIntent().getDoubleExtra("food_price", 0.0);
        int imageResId = getIntent().getIntExtra("food_image", 0);
        String categoryName = getIntent().getStringExtra("category_name");

        foodImage.setImageResource(imageResId);
        foodName.setText(name);
        foodCategory.setText("Category: " + categoryName);
        foodPrice.setText(String.format("$%.2f", price));

        backButton.setOnClickListener(v -> finish());
        addToCartButton.setOnClickListener(v -> {
            FoodItem foodItem = new FoodItem(
                foodId,
                name,
                price,
                imageResId,
                getIntent().getIntExtra("category_id", -1)
            );
            dbHelper.addToCart(foodItem);
            Toast.makeText(this, name + " added to cart", Toast.LENGTH_SHORT).show();
        });
    }
}