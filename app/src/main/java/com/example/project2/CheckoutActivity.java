package com.example.project2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;




public class CheckoutActivity extends AppCompatActivity {

    String amount, Username;
    TextView txtAmount;
    CartDatabase cartDB;
    Button checkout;

    EditText txtName, txtEmail, txtPhone, txtAddress, txtCardNo, txtCVV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.checkout_form);

        amount = getIntent().getStringExtra("amount");
        Username = getIntent().getStringExtra("username");

        txtAmount = findViewById(R.id.txtAmount);
        txtAmount.setText(amount);
        checkout = findViewById(R.id.btnSubmit);
        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPhone = findViewById(R.id.txtPhone);
        txtAddress = findViewById(R.id.txtAddress);
        txtCardNo = findViewById(R.id.txtCardNo);
        txtCVV = findViewById(R.id.txtCVV);


        cartDB = Room.databaseBuilder(this, CartDatabase.class,
                "Cart").allowMainThreadQueries().build();

        checkout.setOnClickListener(view -> {

            if (TextUtils.isEmpty(txtName.getText().toString().trim())) {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(txtEmail.getText().toString().trim())) {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(txtEmail.getText().toString().trim()).matches()) {
                Toast.makeText(this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(txtAddress.getText().toString().trim())) {
                Toast.makeText(this, "Please enter your address", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(txtPhone.getText().toString().trim())) {
                Toast.makeText(this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
                return;
            }


            if (TextUtils.isEmpty(txtCardNo.getText().toString().trim())) {
                Toast.makeText(this, "Please enter your card number", Toast.LENGTH_SHORT).show();
                return;
            }

            if (txtCardNo.getText().toString().trim().length() != 16) {
                Toast.makeText(this, "Please enter a valid card number", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(txtCVV.getText().toString().trim())) {
                Toast.makeText(this, "Please enter your CVV", Toast.LENGTH_SHORT).show();
                return;
            }


            if (txtCVV.getText().toString().trim().length() != 3) {
                Toast.makeText(this, "Please enter a valid CVV", Toast.LENGTH_SHORT).show();
                return;
            }


            String subject = "Order Confirmation";
            String body = "Thank you for placing your order. Your order details are as follows...";


            cartDB.cartDao().clearCart(Username);
            Toast.makeText(this, "Order Placed", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CheckoutActivity.this, ThankyouActivity.class);

            intent.putExtra("username", Username);
            startActivity(intent);

        });

        txtCardNo.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {  // User has left the EditText (focus lost)
                String cardNo = txtCardNo.getText().toString().trim();

                if (!TextUtils.isEmpty(cardNo) && cardNo.length() == 16) {
                    // Mask the card number when focus is lost
                    String maskedCardNo = maskCardNumber(cardNo);
                    txtCardNo.setText(maskedCardNo);
                }
            }
        });

    }

    // Method to partially mask the card number
    private String maskCardNumber(String cardNumber) {
        if (cardNumber.length() < 16) {
            return cardNumber;
        }

        // Keep last 4 digits visible, mask the rest
        return "************" + cardNumber.substring(cardNumber.length() - 4);
    }
}