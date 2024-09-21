package com.example.project2;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

public class productActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    ProductsDatabase productDB;
    ArrayList<ProductsDB> productList;

    Button btnGoCart, btnLogOut;

    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.products);
        user = getIntent().getStringExtra("username");


        recyclerView = findViewById(R.id.rView);
        btnGoCart = findViewById(R.id.btnGoCart);
        btnLogOut = findViewById(R.id.btnLogOut);

        int span = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 1 : 2;
        layoutManager = new GridLayoutManager(this, span);
        recyclerView.setLayoutManager(layoutManager);



//        RoomDatabase.Callback myCallBack = new RoomDatabase.Callback() {
//            @Override
//            public void onCreate(@NonNull SupportSQLiteDatabase db) {
//                super.onCreate(db);
//            }
//
//            @Override
//            public void onOpen(@NonNull SupportSQLiteDatabase db) {
//                super.onOpen(db);
//            }
//        };

        productDB = Room.databaseBuilder(getApplicationContext(), ProductsDatabase.class,
                "Products").allowMainThreadQueries().build();


        if (productDB.productsDao().listProducts().size() == 0) {
            ProductsDB p1 = new ProductsDB(
                    "image1",
                    "image2",
                    "image3",
                    "Blue Green Sports Shoe",
                    299.99,
                    "Lightweight and breathable, these shoes are built for peak performance.",
                    "Experience ultimate comfort and support with these high-performance athletic shoes, designed for both training and casual wear. The breathable mesh upper keeps your feet cool and dry, while the responsive cushioning provides excellent shock absorption. Whether you're hitting the gym or going for a run, these shoes will help you achieve your fitness goals.",
                    "Footwear");
            productDB.productsDao().addProduct(p1);

            ProductsDB p2 = new ProductsDB(
                    "image4",
                    "image5",
                    "image6",
                    "Red Running Hoodie",
                    149.99,
                    "Stay warm and comfortable during your runs with this stylish hoodie.",
                    "This lightweight and breathable hoodie is perfect for your outdoor runs. The moisture-wicking fabric helps keep you dry, while the adjustable hood provides added warmth and protection from the elements. The stylish design and comfortable fit make it a versatile piece for your active wardrobe.",
                    "Apparel");
            productDB.productsDao().addProduct(p2);

            ProductsDB p3 = new ProductsDB(
                    "image7",
                    "image8",
                    "image9",
                    "Black Fitness Watch",
                    249.99,
                    "Monitor your heart rate, steps, calories burned, and sleep patterns with this advanced fitness tracker. Features a sleek design and long battery life.",
                    "Track your fitness goals with precision using this advanced fitness watch. It monitors heart rate, steps, calories burned, sleep patterns, and more. The sleek design and comfortable fit make it a perfect companion for your workouts. With its long battery life, you can focus on your fitness without worrying about charging.",
                    "Electronics");
            productDB.productsDao().addProduct(p3);

            ProductsDB p4 = new ProductsDB(
                    "image10",
                    "image11",
                    "image12",
                    "Green Yoga Mat",
                    49.99,
                    "Find your balance and comfort with this eco-friendly yoga mat.",
                    "Experience optimal comfort and support during your yoga practice with this eco-friendly yoga mat. Made from sustainable materials, it provides excellent grip and cushioning. The non-slip surface ensures stability, while the lightweight design makes it easy to carry to your favorite yoga studio.",
                    "Fitness Equipment");
            productDB.productsDao().addProduct(p4);

            ProductsDB p5 = new ProductsDB(
                    "image13",
                    "image14",
                    "image15",
                    "Black Sports Shoe",
                    329.99,
                    "Lightweight and breathable, these shoes are built for peak performance.",
                    "Experience ultimate comfort and support with these high-performance athletic shoes, designed for both training and casual wear. The breathable mesh upper keeps your feet cool and dry, while the responsive cushioning provides excellent shock absorption. Whether you're hitting the gym or going for a run, these shoes will help you achieve your fitness goals.",
                    "Footwear");
            productDB.productsDao().addProduct(p5);

            ProductsDB p6 = new ProductsDB(
                    "image16",
                    "image17",
                    "image18",
                    "Red Sports Shoe",
                    299.99,
                    "Experience ultimate comfort and support with these high-performance athletic shoes.",
                    "Designed for athletes seeking maximum performance, these shoes offer exceptional comfort and support. The breathable mesh upper keeps your feet cool and dry, while the responsive cushioning absorbs impact and propels you forward. Whether you're training for a marathon or playing your favorite sport, these shoes will help you reach your full potential.",
                    "Footwear");
            productDB.productsDao().addProduct(p6);

            ProductsDB p7 = new ProductsDB(
                    "image19",
                    "image20",
                    "image21",
                    "Gray Workout Gloves",
                    19.99,
                    "Improve your grip and protect your hands during intense workouts.",
                    "Enhance your workout performance with these comfortable and durable workout gloves. The padded palms provide excellent grip and protection, while the breathable material helps keep your hands cool and dry. The adjustable wrist straps ensure a secure fit, allowing you to focus on your workout without distractions.",
                    "Footwear");
            productDB.productsDao().addProduct(p7);

            ProductsDB p8 = new ProductsDB(
                    "image22",
                    "image23",
                    "image24",
                    "Whey Protein Powder",
                    39.99,
                    "Build muscle and recover faster with this high-quality protein supplement.",
                    "Fuel your muscle growth and recovery with this premium whey protein powder. Packed with essential amino acids, it supports lean muscle development and helps repair tissue after workouts. The delicious flavor options make it easy to incorporate into your daily routine.",
                    "Nutrition");
            productDB.productsDao().addProduct(p8);
        }


        productList = (ArrayList<ProductsDB>) productDB.productsDao().listProducts();
        mAdapter = new ProductsAdapter(productList, user);
        recyclerView.setAdapter(mAdapter);


        btnGoCart.setOnClickListener(v -> {
            Intent intent = new Intent(productActivity.this, CartActivity.class);
            intent.putExtra("username", user);
            startActivity(intent);
        });

        btnLogOut.setOnClickListener(v -> {
            Intent intent = new Intent(productActivity.this, MainActivity.class);
            startActivity(intent);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        productList = (ArrayList<ProductsDB>) productDB.productsDao().listProducts();
        mAdapter = new ProductsAdapter(productList, user);
        recyclerView.setAdapter(mAdapter);
    }

}