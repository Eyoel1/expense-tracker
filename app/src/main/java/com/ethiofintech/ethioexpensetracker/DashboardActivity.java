package com.ethiofintech.ethioexpensetracker;


import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ethiofintech.ethioexpensetracker.network.ApiClient;
import com.ethiofintech.ethioexpensetracker.network.SyncManager;
import com.ethiofintech.ethioexpensetracker.utils.SessionManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    private TextView tvTotalBalance, tvIncome, tvExpense;
    private RecyclerView rvTransactions;
    private FloatingActionButton fabAdd;
    private TransactionAdapter adapter;
    private SessionManager sessionManager;
    private List<Map<String, Object>> transactionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // 1. Initialize Views
        tvTotalBalance = findViewById(R.id.tvTotalBalance);
        tvIncome = findViewById(R.id.tvIncome);
        tvExpense = findViewById(R.id.tvExpense);
        rvTransactions = findViewById(R.id.rvTransactions);
        fabAdd = findViewById(R.id.fabAdd);

        sessionManager = new SessionManager(this);

        // 2. Setup RecyclerView
        rvTransactions.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TransactionAdapter(transactionList);
        rvTransactions.setAdapter(adapter);

        // 3. Floating Button Action
        fabAdd.setOnClickListener(v -> {
            startActivity(new Intent(DashboardActivity.this, AddTransactionActivity.class));
        });

        // 4. Manual Sync Trigger
        new SyncManager(this).syncUnsyncedTransactions();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh data every time user returns to Dashboard
        fetchDashboardData();
    }

    private void fetchDashboardData() {
        long userId = sessionManager.getUserId();

        ApiClient.getApiService().getTransactions(userId).enqueue(new Callback<List<Map<String, Object>>>() {
            @Override
            public void onResponse(Call<List<Map<String, Object>>> call, Response<List<Map<String, Object>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    transactionList.clear();
                    transactionList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    calculateSummary(transactionList);
                }
            }

            @Override
            public void onFailure(Call<List<Map<String, Object>>> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, "Offline Mode: Showing cached data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void calculateSummary(List<Map<String, Object>> transactions) {
        double income = 0;
        double expense = 0;

        for (Map<String, Object> t : transactions) {
            double amt = (double) t.get("amount");
            String type = (String) t.get("type");

            if ("INCOME".equals(type)) income += amt;
            else expense += amt;
        }

        double balance = income - expense;

        // Display in Ethiopian Birr
        tvTotalBalance.setText(String.format("%.2f ETB", balance));
        tvIncome.setText(String.format("%.2f ETB", income));
        tvExpense.setText(String.format("%.2f ETB", expense));
    }
}