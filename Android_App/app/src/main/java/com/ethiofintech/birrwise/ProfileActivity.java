package com.ethiofintech.birrwise;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.ethiofintech.birrwise.network.RetrofitClient;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private EditText etSalary;
    private Button btnUpdate, btnLogout;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userId = getIntent().getStringExtra("USER_ID");
        etSalary = findViewById(R.id.etMonthlySalary);
        btnUpdate = findViewById(R.id.btnUpdateProfile);
        btnLogout = findViewById(R.id.btnLogout);

        // Update Salary Logic
        btnUpdate.setOnClickListener(v -> {
            String salaryStr = etSalary.getText().toString();
            if(salaryStr.isEmpty()) return;

            JsonObject data = new JsonObject();
            data.addProperty("monthlySalary", Double.parseDouble(salaryStr));

            RetrofitClient.getService().updateProfile(userId, data).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(ProfileActivity.this, "Salary Set! Budget Updated.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(ProfileActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                }
            });
        });

        // Logout Logic
        btnLogout.setOnClickListener(v -> {
            // In a real app, clear shared preferences tokens here
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            // Clear history so pressing 'Back' doesn't return to profile
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }
}