package com.example.resturant_order;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    EditText nameInput;
    Button signInButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_view);
        nameInput = findViewById(R.id.nameInput);
        signInButton = findViewById(R.id.signInButton);

        signInButton.setOnClickListener(v -> {
            String name = nameInput.getText().toString();
            if (!name.isEmpty()) {
                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                intent.putExtra("username", name);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
            }
        });
    }
}