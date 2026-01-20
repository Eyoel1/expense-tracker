package com.ethiofintech.birrwise;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ethiofintech.birrwise.network.RetrofitClient;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EdirAdapter extends RecyclerView.Adapter<EdirAdapter.EdirViewHolder> {

    private List<String> months; // e.g., ["Meskerem", "Tikimt"]
    private Context context;
    private String userId;

    public EdirAdapter(Context context, List<String> months, String userId) {
        this.context = context;
        this.months = months;
        this.userId = userId;
    }

    @NonNull
    @Override
    public EdirViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_edir_month, parent, false);
        return new EdirViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EdirViewHolder holder, int position) {
        String month = months.get(position);
        holder.tvMonthName.setText(month);

        // Click Logic
        holder.btnPay.setOnClickListener(v -> {
            // 1. Create Data for API
            JsonObject data = new JsonObject();
            data.addProperty("amount", 100.0); // Fixed Edir Price
            data.addProperty("category", "Edir");
            data.addProperty("description", "Contribution for " + month);
            data.addProperty("paymentMethod", "Cash");
            data.addProperty("userId", Long.parseLong(userId));

            // 2. Send to Backend
            RetrofitClient.getService().addExpense(data).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "Paid for " + month, Toast.LENGTH_SHORT).show();

                        // 3. Visual Feedback (Robustness)
                        holder.btnPay.setText("PAID");
                        holder.btnPay.setBackgroundColor(Color.parseColor("#2E7D32")); // Green
                        holder.btnPay.setEnabled(false); // Can't pay twice
                    }
                }
                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(context, "Network Error", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return months.size();
    }

    public static class EdirViewHolder extends RecyclerView.ViewHolder {
        TextView tvMonthName;
        Button btnPay;

        public EdirViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMonthName = itemView.findViewById(R.id.tvMonthName);
            btnPay = itemView.findViewById(R.id.btnPayEdir);
        }
    }
}