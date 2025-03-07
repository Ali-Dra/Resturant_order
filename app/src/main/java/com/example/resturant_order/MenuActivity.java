package com.example.resturant_order;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MenuActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FoodCategoryAdapter adapter;
    List<FoodCategory> categoryList;
    TextView customerName;
    DatabaseHelper dbHelper;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_view);

        dbHelper = new DatabaseHelper(this);

        String username = getIntent().getStringExtra("username");

        customerName = findViewById(R.id.user);
        if (username != null && !username.isEmpty()) {
            customerName.setText("Welcome, " + username + "!");
        }

        ImageView cartIcon = findViewById(R.id.cart_icon);
        cartIcon.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, CartActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        });

        ImageView logoutIcon = findViewById(R.id.logout_icon);
        logoutIcon.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        recyclerView = findViewById(R.id.categoriesList);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // 2 columns

        categoryList = dbHelper.getAllCategories();

        adapter = new FoodCategoryAdapter(this, categoryList, this::onCategoryClick);
        recyclerView.setAdapter(adapter);
    }

    private void onCategoryClick(FoodCategory category) {
        Intent intent = new Intent(MenuActivity.this, FoodListActivity.class);
        intent.putExtra("categoryId", category.getId());
        intent.putExtra("categoryName", category.getName());
        startActivity(intent);
    }
}