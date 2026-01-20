package com.ethiofintech.birrwise;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.ethiofintech.birrwise.network.RetrofitClient;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReceiptActivity extends AppCompatActivity {

    private TextView tvIncome, tvExpense, tvNetResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        tvIncome = findViewById(R.id.tvIncome);
        tvExpense = findViewById(R.id.tvExpense);
        tvNetResult = findViewById(R.id.tvNetResult);

        String userId = getIntent().getStringExtra("USER_ID");
        loadData(userId);
    }

    private void loadData(String userId) {
        RetrofitClient.getService().getSummary(userId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    double inc = response.body().get("income").getAsDouble();
                    double exp = response.body().get("totalExpense").getAsDouble();
                    double bal = response.body().get("balance").getAsDouble();

                    tvIncome.setText(String.format("%,.2f", inc));
                    tvExpense.setText(String.format("%,.2f", exp));
                    tvNetResult.setText(String.format("%,.2f Br", bal));

                    // Frontend Polish: Change color if negative
                    if (bal < 0) {
                        tvNetResult.setTextColor(android.graphics.Color.RED);
                    } else {
                        tvNetResult.setTextColor(android.graphics.Color.parseColor("#2E7D32")); // Green
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(ReceiptActivity.this, "Data Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}