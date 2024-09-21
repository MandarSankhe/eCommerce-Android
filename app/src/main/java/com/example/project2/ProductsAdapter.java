package com.example.project2;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;


import java.io.Serializable;
import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private static ArrayList<ProductsDB> products;
    private static String user = "";
    static CartDatabase cartdatabase;

    public ProductsAdapter(ArrayList<ProductsDB> products, String user) {

        this.products = products;
        this.user = user;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductsDB product = products.get(position);
        holder.imgProduct.setImageResource(holder.itemView.getContext().getResources().getIdentifier(product.getMainImage(), "drawable", holder.itemView.getContext().getPackageName()));
        holder.txtName.setText(product.getName());
        holder.txtShortDesc.setText(product.getShortDescription());
        holder.txtPrice.setText("$" + Double.toString(product.getPrice()));

        // Check if item already exists in Cart for this user and product ID
        boolean itemExists = cartdatabase.cartDao().checkIfItemExists(user, product.getId());
        holder.btnAddToCart.setEnabled(!itemExists);

        if (itemExists) {
            holder.btnAddToCart.setText("Added to cart!");
            holder.btnAddToCart.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.PrimaryColorDeselected));
        } else {
            holder.btnAddToCart.setText("Add to Cart");
        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView txtName, txtShortDesc, txtPrice;

        CardView cardView;
        Button btnAddToCart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.productImage);
            txtName = itemView.findViewById(R.id.txtTitle);
            txtShortDesc = itemView.findViewById(R.id.txtShortDescription);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            cardView = itemView.findViewById(R.id.cardView);
            btnAddToCart = itemView.findViewById(R.id.btnAddCart);

            // Set onClickListener here
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        int position = getAdapterPosition(); // Get the item position

                        ProductsDB product = products.get(position);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("product", (Serializable) product);

                        Intent intent = new Intent(v.getContext(), ProductDetailActivity.class);
                        intent.putExtras(bundle);
                        intent.putExtra("user", user);
                        v.getContext().startActivity(intent);

                }
            });

            cartdatabase = Room.databaseBuilder(itemView.getContext(), CartDatabase.class,
                    "Cart").allowMainThreadQueries().build();

            btnAddToCart.setOnClickListener(v -> {

                int position = getAdapterPosition();

                ProductsDB product = products.get(position);
                CartDB cartItem = new CartDB(user, product.getId(), 1);
                cartdatabase.cartDao().addProduct(cartItem);
                btnAddToCart.setEnabled(false);
                btnAddToCart.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.PrimaryColorDeselected));
                btnAddToCart.setText("Added to cart!");
            });

        }


    }


}
