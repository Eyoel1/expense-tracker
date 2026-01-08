package com.ethiofintech.ethioexpensetracker;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.ethiofintech.ethioexpensetracker.data.local.AppDatabase;
import com.ethiofintech.ethioexpensetracker.data.local.LocalTransaction;
import com.ethiofintech.ethioexpensetracker.network.SyncManager;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AddTransactionActivity extends AppCompatActivity {

    private EditText etAmount, etDescription;
    private Spinner spCategory, spType;
    private Button btnSave;
    private final Executor executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        etAmount = findViewById(R.id.etAmount);
        etDescription = findViewById(R.id.etDescription);
        spCategory = findViewById(R.id.spinnerCategory);
        spType = findViewById(R.id.spinnerType); // INCOME or EXPENSE
        btnSave = findViewById(R.id.btnSave);

        // Populate Category Spinner
        String[] categories = {"Food/Injera", "Transport/Ride", "Rent", "Salary", "Shopping", "Others"};
        ArrayAdapter<String> catAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        spCategory.setAdapter(catAdapter);

        // Populate Type Spinner
        String[] types = {"EXPENSE", "INCOME"};
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, types);
        spType.setAdapter(typeAdapter);

        btnSave.setOnClickListener(v -> saveTransaction());
    }

    private void saveTransaction() {
        String amountStr = etAmount.getText().toString();
        String desc = etDescription.getText().toString();
        String category = spCategory.getSelectedItem().toString();
        String type = spType.getSelectedItem().toString();

        if (amountStr.isEmpty()) {
            etAmount.setError("Required");
            return;
        }

        double amount = Double.parseDouble(amountStr);

        // 1. Create Local Transaction Object
        LocalTransaction lt = new LocalTransaction();
        lt.amount = amount;
        lt.description = category + ": " + desc;
        lt.type = type;
        lt.isSynced = false;

        // 2. Save to Room (Offline Storage)
        executor.execute(() -> {
            AppDatabase.getInstance(this).transactionDao().insert(lt);

            // 3. Trigger Sync to Server
            new SyncManager(this).syncUnsyncedTransactions();

            runOnUiThread(() -> {
                Toast.makeText(this, "Saved Locally!", Toast.LENGTH_SHORT).show();
                finish(); // Go back to Dashboard
            });
        });
    }
}