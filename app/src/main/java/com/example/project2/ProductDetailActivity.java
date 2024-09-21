package com.example.project2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {

    TextView title, price, description;
    EditText txtQuantity;
    ImageView mainImage, secondImage, thirdImage;
    ProductsDatabase productDB;
    CartDatabase cartdatabase;
    Button btnAddToCart, btnDecreaseQuantity, btnIncreaseQuantity, btnRemove, btnGoCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.product_detail);

        title = findViewById(R.id.productName);
        mainImage = findViewById(R.id.mainImage);
        secondImage = findViewById(R.id.secondImage);
        thirdImage = findViewById(R.id.thirdImage);
        price = findViewById(R.id.txtPrice1);
        description = findViewById(R.id.txtLongDescription);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        btnDecreaseQuantity = findViewById(R.id.btnDecreaseQuantity);
        btnIncreaseQuantity = findViewById(R.id.btnIncreaseQuantity);
        btnRemove = findViewById(R.id.btnRemove);
        btnGoCart = findViewById(R.id.btnCart);
        txtQuantity = findViewById(R.id.txtQuantity1);

        List<ProductsDB> arrImages;
        productDB = Room.databaseBuilder(getApplicationContext(), ProductsDatabase.class,
                "Products").allowMainThreadQueries().build();
        cartdatabase = Room.databaseBuilder(getApplicationContext(), CartDatabase.class,
                "Cart").allowMainThreadQueries().build();

        Bundle bundle = getIntent().getExtras();
        String user = getIntent().getStringExtra("user");
        ProductsDB product = (ProductsDB) bundle.getSerializable("product");

        title.setText(product.getName());
        description.setText(product.getLongDescription());
        arrImages = productDB.productsDao().getImagesByProductId(product.getId());
        Double Textprice = product.getPrice();


        mainImage.setImageResource(getResources().getIdentifier(arrImages.get(0).getMainImage(), "drawable", getPackageName()));
        secondImage.setImageResource(getResources().getIdentifier(arrImages.get(0).getSecondImage(), "drawable", getPackageName()));
        thirdImage.setImageResource(getResources().getIdentifier(arrImages.get(0).getThirdImage(), "drawable", getPackageName()));
        price.setText("Price: $" + Textprice);

        boolean itemExists = cartdatabase.cartDao().checkIfItemExists(user, product.getId());
        if (itemExists) {
            int existingQuantity = cartdatabase.cartDao().getExistingQuantity(user, product.getId());
            txtQuantity.setText(String.valueOf(existingQuantity + 1));
            btnRemove.setVisibility(View.VISIBLE);
        }
        else {
            txtQuantity.setText("1");
            btnRemove.setVisibility(View.GONE);
        }


        btnDecreaseQuantity.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(txtQuantity.getText().toString());
            if (currentQuantity > 1) {
                currentQuantity--;
                txtQuantity.setText(String.valueOf(currentQuantity));
            }
        });

        btnIncreaseQuantity.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(txtQuantity.getText().toString());
            currentQuantity++;
            txtQuantity.setText(String.valueOf(currentQuantity));

        });

        btnAddToCart.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(txtQuantity.getText().toString());


            if (itemExists) {
                // Item exists, update quantity
                cartdatabase.cartDao().updateQuantity(currentQuantity, user, product.getId());
                Toast.makeText(v.getContext(), "Cart Updated!", Toast.LENGTH_SHORT).show();
            } else {
                // Item doesn't exist, add to cart
                CartDB cartItem = new CartDB(user, product.getId(), currentQuantity);
                cartdatabase.cartDao().addProduct(cartItem);
                Toast.makeText(v.getContext(), "Added to cart!", Toast.LENGTH_SHORT).show();
            }
            finish();
            startActivity(getIntent());


        });

        btnRemove.setOnClickListener(v -> {
            cartdatabase.cartDao().removeProduct(user, product.getId());
            Toast.makeText(v.getContext(), "Removed from cart!", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(getIntent());
        });

        List <CartDB> cartItemsList = (List<CartDB>) cartdatabase.cartDao().getProductByUser(user);

        btnGoCart.setOnClickListener(v -> {

            if (cartItemsList.isEmpty()) {
                Toast.makeText(v.getContext(), "Your cart is empty!", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(this, CartActivity.class);
            intent.putExtra("username", user);
            startActivity(intent);
        } );


    }
}