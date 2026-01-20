package com.ethiofintech.birrwise;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.ethiofintech.birrwise.network.RetrofitClient;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText etName, etEmail, etPassword;
    private Button btnDoRegister;
    private TextView tvLoginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etRegEmail);
        etPassword = findViewById(R.id.etRegPassword);
        btnDoRegister = findViewById(R.id.btnDoRegister);
        tvLoginLink = findViewById(R.id.tvLoginLink);

        // 1. Sign Up Button Click
        btnDoRegister.setOnClickListener(v -> performRegistration());

        // 2. Back to Login Click
        tvLoginLink.setOnClickListener(v -> finish()); // Just closes this screen to go back
    }

    private void performRegistration() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Prepare JSON Data
        JsonObject userData = new JsonObject();
        userData.addProperty("fullName", name);
        userData.addProperty("email", email);
        userData.addProperty("password", password);

        // Send to Spring Boot Backend
        Call<JsonObject> call = RetrofitClient.getService().registerUser(userData);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Registration Successful! Please Login.", Toast.LENGTH_LONG).show();
                    finish(); // Go back to Login Screen automatically
                } else {
                    Toast.makeText(RegisterActivity.this, "Registration Failed (Email already exists?)", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}