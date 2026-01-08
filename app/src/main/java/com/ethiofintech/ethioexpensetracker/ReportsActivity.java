package com.ethiofintech.ethioexpensetracker;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.ethiofintech.ethioexpensetracker.network.ApiClient;
import com.ethiofintech.ethioexpensetracker.utils.SessionManager;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.HashMap;
import java.util.Map;

public class ReportsActivity extends AppCompatActivity {
    PieChart pieChart;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        pieChart = findViewById(R.id.pieChart);
        sessionManager = new SessionManager(this);

        fetchChartData();
    }

    private void fetchChartData() {
        long userId = sessionManager.getUserId();

        // We will call our summary API
        ApiClient.getApiService().getTransactions(userId).enqueue(new Callback<List<Map<String, Object>>>() {
            @Override
            public void onResponse(Call<List<Map<String, Object>>> call, Response<List<Map<String, Object>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    setupPieChart(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<Map<String, Object>>> call, Throwable t) {}
        });
    }

    private void setupPieChart(List<Map<String, Object>> data) {
        List<PieEntry> entries = new ArrayList<>();

        // Convert transaction data into Pie Entries
        for (Map<String, Object> map : data) {
            String label = (String) map.get("description");
            double value = (double) map.get("amount");
            entries.add(new PieEntry((float) value, label));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Expenses");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(12f);

        PieData pieData = new PieData(dataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.animateY(1000);
        pieChart.invalidate();
    }
}