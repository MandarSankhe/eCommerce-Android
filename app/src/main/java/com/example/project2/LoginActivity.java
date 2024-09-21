package com.example.project2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText editEmail, editPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        btnLogin = findViewById(R.id.btnLogin);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);

        btnLogin.setOnClickListener(v -> {
            String email = editEmail.getText().toString().trim();
            String password = editPassword.getText().toString().trim();

            // Validate email and password
            if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches() || TextUtils.isEmpty(password)) {
                // Handle empty or invalid email/password
                Toast.makeText(LoginActivity.this, "Invalid Input: " , Toast.LENGTH_SHORT).show();
                return;
            }

            // create user and handle existing accounts
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // User created successfully, show success message and redirect to productActivity
                            Toast.makeText(LoginActivity.this, "Account created successfully!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, productActivity.class);
                            intent.putExtra("username", email);
                            startActivity(intent);
                            finish();
                        } else {
                            // User creation failed, check for existing account
                            String errorMessage = task.getException().getMessage();
                            if (errorMessage.contains("The email address is already in use by another account.")) {
                                // Existing account with different provider, sign in with email/password
                                signInWithEmailAndPassword(email, password);
                            } else {
                                // Unexpected error during user creation
                                Toast.makeText(LoginActivity.this, "Registration failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });
    }

    private void signInWithEmailAndPassword(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in successful, proceed to productActivity
                        Intent intent = new Intent(LoginActivity.this, productActivity.class);
                        intent.putExtra("username", email);
                        startActivity(intent);
                        finish();
                    } else {
                        // Sign in failed, handle error (optional)
                        Toast.makeText(LoginActivity.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
