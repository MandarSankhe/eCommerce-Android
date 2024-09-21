package com.example.project2;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements CartItemRemovedListener {

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    TextView txtTotalCartPrice, txtTotalTax, txtTotalAmount;
    double totalCartPrice = 0.0;
    double tax = 0.0;
    CartDatabase cartDB;
    ProductsDatabase productsDatabase;
    List<CartDB> cartItemsList;
    String user;
    Button btnCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        user = getIntent().getStringExtra("username");
        recyclerView = findViewById(R.id.rViewCart);
        int spanCount = getResources().getConfiguration()
                .orientation == Configuration.ORIENTATION_PORTRAIT ? 1 : 2; // Adjust spanCount as needed
        layoutManager = new GridLayoutManager(this, spanCount);
        recyclerView.setLayoutManager(layoutManager);
        cartDB = Room.databaseBuilder(this, CartDatabase.class,
                "Cart").allowMainThreadQueries().build();
        productsDatabase = Room.databaseBuilder(getApplicationContext(), ProductsDatabase.class,
                "Products").allowMainThreadQueries().build();
        cartItemsList = (List<CartDB>) cartDB.cartDao().getProductByUser(user);

        mAdapter = new CartAdapter(cartItemsList, user, this);
        recyclerView.setAdapter(mAdapter);

        txtTotalCartPrice = findViewById(R.id.txtTotalCartPrice);
        txtTotalTax = findViewById(R.id.txtTax);
        txtTotalAmount = findViewById(R.id.txtTotalAfterTax);
        btnCheckout = findViewById(R.id.btnCheckout);

        calculateTotalCartPrice();




        btnCheckout.setOnClickListener(v -> {
            if (cartItemsList.isEmpty()) {
                Toast.makeText(this, "Cart is empty", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
            intent.putExtra("amount", txtTotalAmount.getText().toString());
            intent.putExtra("username", user);
            startActivity(intent);
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        cartItemsList = (List<CartDB>) cartDB.cartDao().getProductByUser(user);
        mAdapter = new CartAdapter(cartItemsList, user, this);
        recyclerView.setAdapter(mAdapter);
        //calculateTotalCartPrice();
    }

    @Override
    public void onCartItemRemoved() {
        cartItemsList = (List<CartDB>) cartDB.cartDao().getProductByUser(user);
        mAdapter.notifyDataSetChanged();
        calculateTotalCartPrice();
    }

    private void calculateTotalCartPrice() {
        totalCartPrice = 0.0;
        tax = 0.0;
        for (int i = 0; i < cartItemsList.size(); i++) {
            CartDB cartItem = cartItemsList.get(i);
            ProductsDB product = productsDatabase.productsDao().getProductById(cartItem.getProductId());
            double unitPrice = product.getPrice();
            int quantity = cartItem.getQuantity();
            totalCartPrice += unitPrice * quantity;
        }
        tax = totalCartPrice * 0.13;
        txtTotalCartPrice.setText("Total: $" + String.format("%.2f", totalCartPrice));
        txtTotalTax.setText("Tax (13%): $" + String.format("%.2f", tax));
        txtTotalAmount.setText("Total Amount: $" + String.format("%.2f", totalCartPrice + tax));
    }

}