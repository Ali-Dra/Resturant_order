package com.example.resturant_order;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FoodListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FoodItemAdapter adapter;
    List<FoodItem> foodItemList;
    DatabaseHelper dbHelper;
    TextView categoryTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_items_category);

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        dbHelper = new DatabaseHelper(this);
        int categoryId = getIntent().getIntExtra("categoryId", -1);
        String categoryName = getIntent().getStringExtra("categoryName");
        categoryTitle = findViewById(R.id.categoryNameTitle);
        categoryTitle.setText(categoryName);
        recyclerView = findViewById(R.id.foodItemsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        foodItemList = dbHelper.getFoodItemsByCategory(categoryId);adapter = new FoodItemAdapter(this, foodItemList);
        recyclerView.setAdapter(adapter);
    }
}