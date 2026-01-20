package com.ethiofintech.birrwise;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ethiofintech.birrwise.network.RetrofitClient;
import com.google.gson.JsonObject;

import java.time.LocalDate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    // UI Variables
    private TextView tvTotalBalance, tvDateDisplay;
    private Button btnAddExpense, btnViewReport, btnViewStats, btnEdir, btnProfile;

    // Logic Variables
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // 1. Receive User ID
        userId = getIntent().getStringExtra("USER_ID");
        if (userId == null) {
            Toast.makeText(this, "Session Error. Re-login.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // 2. Connect to XML (Use safe initialization)
        tvTotalBalance = findViewById(R.id.tvTotalBalance);
        tvDateDisplay = findViewById(R.id.tvDateDisplay);

        btnAddExpense = findViewById(R.id.btnAddExpense);
        btnViewReport = findViewById(R.id.btnViewReport);
        btnViewStats = findViewById(R.id.btnViewStats);
        btnEdir = findViewById(R.id.btnEdir); // Ensure these exist in your XML or comment out
        btnProfile = findViewById(R.id.btnProfile);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            tvDateDisplay.setText("Date: " + LocalDate.now().toString());
        } else {
            tvDateDisplay.setText("Welcome to BirrWise");
        }

      if (btnAddExpense != null) {
            btnAddExpense.setOnClickListener(v -> {
                Intent intent = new Intent(DashboardActivity.this, AddExpenseActivity.class);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
            });
        }

       if (btnViewReport != null) {
            btnViewReport.setOnClickListener(v -> {
                Intent intent = new Intent(DashboardActivity.this, ReceiptActivity.class);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
            });
        }

        if (btnViewStats != null) {
            btnViewStats.setOnClickListener(v -> {
                Intent intent = new Intent(DashboardActivity.this, StatsActivity.class);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
            });
        }

       if (btnEdir != null) {
            btnEdir.setOnClickListener(v -> {
                Intent intent = new Intent(DashboardActivity.this, EdirActivity.class);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
            });
        }

       if (btnProfile != null) {
            btnProfile.setOnClickListener(v -> {
                Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchDashboardData();
    }

    private void fetchDashboardData() {
        if (userId == null) return;

        RetrofitClient.getService().getSummary(userId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    double balance = response.body().get("balance").getAsDouble();
                    // Professional Formatting: "1,500.50 Br"
                    tvTotalBalance.setText(String.format("%,.2f Br", balance));
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("DashError", t.getMessage());
            }
        });
    }
}