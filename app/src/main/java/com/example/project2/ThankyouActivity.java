package com.example.project2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ThankyouActivity extends AppCompatActivity {

    Button products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thankyou);

        products = findViewById(R.id.btnProdcuts);
        String Username = getIntent().getStringExtra("username");
        products.setOnClickListener(v -> {
            Intent intent = new Intent(ThankyouActivity.this, productActivity.class);

            intent.putExtra("username", Username);
            startActivity(intent);
        });


    }
}