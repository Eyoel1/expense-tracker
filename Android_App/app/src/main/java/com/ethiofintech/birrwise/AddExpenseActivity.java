package com.ethiofintech.birrwise;


import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.ethiofintech.birrwise.network.RetrofitClient;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddExpenseActivity extends AppCompatActivity {

    private EditText etAmount, etDescription;
    private Spinner spinnerCategory;
    private RadioGroup rgPayment;
    private Button btnSaveExpense;
    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        currentUserId = getIntent().getStringExtra("USER_ID");


        etAmount = findViewById(R.id.etAmount);
        etDescription = findViewById(R.id.etDescription);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        rgPayment = findViewById(R.id.rgPayment);
        btnSaveExpense = findViewById(R.id.btnSaveExpense);

        String[] categories = {
                "Food / Groceries (Injera)",
                "Transport (Taxi/Bajaj)",
                "Rent / Housing",
                "Mobile (EthioTel/Data)",
                "Utilities (Water/Electric)",
                "Entertainment",
                "Social (Edir/Leqso)",
                "Other"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        spinnerCategory.setAdapter(adapter);


        btnSaveExpense.setOnClickListener(v -> saveExpense());
    }

    private void saveExpense() {
        String amountStr = etAmount.getText().toString();
        String desc = etDescription.getText().toString();

        if (amountStr.isEmpty()) {
            etAmount.setError("Required");
            return;
        }

        String paymentMethod = "Cash";
        int selectedId = rgPayment.getCheckedRadioButtonId();
        if (selectedId == R.id.rbTelebirr) paymentMethod = "Telebirr";
        else if (selectedId == R.id.rbCbe) paymentMethod = "CBE Birr";

        JsonObject expenseData = new JsonObject();
        expenseData.addProperty("amount", Double.parseDouble(amountStr));
        expenseData.addProperty("description", desc);
        expenseData.addProperty("category", spinnerCategory.getSelectedItem().toString());
        expenseData.addProperty("paymentMethod", paymentMethod);
        expenseData.addProperty("userId", Long.parseLong(currentUserId));


        Call<JsonObject> call = RetrofitClient.getService().addExpense(expenseData);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddExpenseActivity.this, "Expense Saved!", Toast.LENGTH_SHORT).show();
                    finish(); // Close activity and return to Dashboard
                } else {
                    Toast.makeText(AddExpenseActivity.this, "Failed to save", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(AddExpenseActivity.this, "Network Error", Toast.LENGTH_LONG).show();
            }
        });
    }
}