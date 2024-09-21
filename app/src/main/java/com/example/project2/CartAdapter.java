package com.example.project2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private static List<CartDB> cartItems;
    private static String user = "";
    private static CartDatabase cartdatabase;
    private ProductsDatabase productsDatabase;
    private CartItemRemovedListener listener;

    public CartAdapter(List<CartDB> cartItems, String user, CartItemRemovedListener listener) {

        this.cartItems = cartItems;
        this.user = user;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_cart_adapter, parent, false);
        return new CartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        CartDB cart = cartItems.get(position);

        productsDatabase = Room.databaseBuilder(holder.itemView.getContext(), ProductsDatabase.class, "Products").allowMainThreadQueries().build();

        ProductsDB product = productsDatabase.productsDao().getProductById(cart.getProductId());

        holder.imgProductSmall.setImageResource(holder.itemView.getContext().getResources().getIdentifier(product.getMainImage(), "drawable", holder.itemView.getContext().getPackageName()));
        holder.txtItemName.setText(product.getName());
        holder.txtUnitPrice.setText("$" + product.getPrice());
        holder.txtQuantity1.setText(Integer.toString(cart.getQuantity()));

    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProductSmall;

        EditText txtQuantity1;
        TextView txtItemName, txtUnitPrice;

        CardView cardView;
        Button btnRemove1, btnDecreaseQuantity, btnIncreaseQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProductSmall = itemView.findViewById(R.id.imgProductSmall);
            txtItemName = itemView.findViewById(R.id.txtItemName);
            txtUnitPrice = itemView.findViewById(R.id.txtUnitPrice);
            cardView = itemView.findViewById(R.id.cardView);
            txtQuantity1 = itemView.findViewById(R.id.txtQuantity1);
            btnDecreaseQuantity = itemView.findViewById(R.id.btnDecreaseQuantity);
            btnIncreaseQuantity = itemView.findViewById(R.id.btnIncreaseQuantity);
            btnRemove1 = itemView.findViewById(R.id.btnRemove1);

            btnIncreaseQuantity.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != -1) {  // Check for valid position
                    CartDB cart = cartItems.get(position);
                    int currentQuantity = cart.getQuantity();
                    currentQuantity++;

                    cart.setQuantity(currentQuantity);
                    cartItems.set(position, cart);
                    cartdatabase.cartDao().updateQuantity(currentQuantity, user, cart.getProductId());
                    notifyItemChanged(position);
                    notifyDataSetChanged();
                    listener.onCartItemRemoved();
                }
            });

            btnDecreaseQuantity.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != -1) {  // Check for valid position
                    CartDB cart = cartItems.get(position);
                    int currentQuantity = cart.getQuantity();
                    if (currentQuantity > 1) {
                        currentQuantity--;
                        cart.setQuantity(currentQuantity);
                        cartItems.set(position, cart);
                        cartdatabase.cartDao().updateQuantity(currentQuantity, user, cart.getProductId());
                        notifyItemChanged(position);
                        notifyDataSetChanged();
                        listener.onCartItemRemoved();
                    }
                }
            });

            cartdatabase = Room.databaseBuilder(itemView.getContext(), CartDatabase.class,
                    "Cart").allowMainThreadQueries().build();

            btnRemove1.setOnClickListener(v -> {
                int position = getAdapterPosition();
                CartDB cart = cartItems.get(position);
                cartdatabase.cartDao().removeProduct(user, cart.getProductId());
                Toast.makeText(v.getContext(), "Removed from cart!", Toast.LENGTH_SHORT).show();
                cartItems.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, cartItems.size());
                listener.onCartItemRemoved();
            });
        }
    }
}