package com.example.resturant_order;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartActivity extends AppCompatActivity implements CartAdapter.CartUpdateListener {
    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private List<CartItem> cartItems;
    private DatabaseHelper dbHelper;
    private TextView totalPriceText;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        username = getIntent().getStringExtra("username");
        dbHelper = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.cartItemsList);
        totalPriceText = findViewById(R.id.totalPrice);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadCartItems();

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        Button createOrderButton = findViewById(R.id.createOrderButton);
        createOrderButton.setOnClickListener(v -> createOrder());
    }

    private void loadCartItems() {
        cartItems = dbHelper.getCartItems();
        adapter = new CartAdapter(this, cartItems, this);
        recyclerView.setAdapter(adapter);
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getPrice() * item.getQuantity();
        }
        totalPriceText.setText(String.format("Total: $%.2f", total));
    }

    private void showOrderConfirmationDialog(int tableNumber) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.order_confirmation_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView customerNameText = dialog.findViewById(R.id.customerNameText);
        TextView tableNumberText = dialog.findViewById(R.id.tableNumberText);
        Button okButton = dialog.findViewById(R.id.okButton);

        customerNameText.setText("Customer: " + username);
        tableNumberText.setText("Table Number: " + tableNumber);

        okButton.setOnClickListener(v -> {
            dialog.dismiss();
            Intent intent = new Intent(CartActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        dialog.setCancelable(false);
        dialog.show();
    }

    private void createOrder() {
        if (cartItems.isEmpty()) {
            Toast.makeText(this, "Your cart is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        long orderId = dbHelper.createOrder(username, cartItems);
        if (orderId != -1) {
            int tableNumber = new Random().nextInt(10) + 1;
            showOrderConfirmationDialog(tableNumber);
        } else {
            Toast.makeText(this, "Failed to create order", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCartUpdated() {
        updateTotalPrice();
    }
}
