package com.ethiofintech.birrwise;


import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.ethiofintech.birrwise.network.RetrofitClient;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatsActivity extends AppCompatActivity {

    private PieChart pieChart;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        pieChart = findViewById(R.id.pieChart);
        userId = getIntent().getStringExtra("USER_ID");

        fetchAndChartData();
    }

    private void fetchAndChartData() {
        RetrofitClient.getService().getAllExpenses(userId).enqueue(new Callback<List<JsonObject>>() {
            @Override
            public void onResponse(Call<List<JsonObject>> call, Response<List<JsonObject>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    processDataForChart(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<JsonObject>> call, Throwable t) {
                Toast.makeText(StatsActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Heavy Logic: Grouping Data
    private void processDataForChart(List<JsonObject> expenseList) {
        Map<String, Float> categoryMap = new HashMap<>();

        // 1. Aggegrate Totals per Category
        for (JsonObject expense : expenseList) {
            String cat = expense.get("category").getAsString();
            float amount = expense.get("amount").getAsFloat();

            // If exists, add to existing; else put new
            if (categoryMap.containsKey(cat)) {
                categoryMap.put(cat, categoryMap.get(cat) + amount);
            } else {
                categoryMap.put(cat, amount);
            }
        }

        // 2. Prepare Pie Entries
        ArrayList<PieEntry> entries = new ArrayList<>();
        for (String cat : categoryMap.keySet()) {
            entries.add(new PieEntry(categoryMap.get(cat), cat));
        }

        // 3. Configure Chart Look
        PieDataSet dataSet = new PieDataSet(entries, "Expense Categories");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS); // Nice Colors
        dataSet.setValueTextSize(16f);
        dataSet.setValueTextColor(Color.WHITE);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);

        // Polish
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("My Spending");
        pieChart.setCenterTextSize(20f);
        pieChart.animateY(1000); // Animation
        pieChart.invalidate(); // Refresh
    }
}