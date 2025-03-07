package com.example.resturant_order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private List<CartItem> cartItems;
    private Context context;
    private DatabaseHelper dbHelper;
    private CartUpdateListener listener;

    public interface CartUpdateListener {
        void onCartUpdated();
    }

    public CartAdapter(Context context, List<CartItem> cartItems, CartUpdateListener listener) {
        this.context = context;
        this.cartItems = cartItems;
        this.dbHelper = new DatabaseHelper(context);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItem item = cartItems.get(position);
        
        holder.nameText.setText(item.getName());
        holder.priceText.setText(String.format("$%.2f", item.getPrice()));
        holder.quantityText.setText("x" + item.getQuantity());

        holder.removeButton.setOnClickListener(v -> {
            dbHelper.removeFromCart(item.getId());
            cartItems.remove(position);
            notifyDataSetChanged();
            listener.onCartUpdated();
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameText, priceText, quantityText;
        ImageButton removeButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.cartItemName);
            priceText = itemView.findViewById(R.id.cartItemPrice);
            quantityText = itemView.findViewById(R.id.cartItemQuantity);
            removeButton = itemView.findViewById(R.id.removeButton);
        }
    }
}