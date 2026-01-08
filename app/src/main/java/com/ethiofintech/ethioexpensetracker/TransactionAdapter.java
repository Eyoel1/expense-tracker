package com.ethiofintech.ethioexpensetracker;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    private final List<Map<String, Object>> transactionList;

    public TransactionAdapter(List<Map<String, Object>> transactionList) {
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Map<String, Object> transaction = transactionList.get(position);

        String desc = (String) transaction.get("description");
        Object amountObj = transaction.get("amount");
        double amount = 0.0;
        if (amountObj instanceof Double) {
            amount = (Double) amountObj;
        } else if (amountObj instanceof Integer) {
            amount = ((Integer) amountObj).doubleValue();
        }

        String type = (String) transaction.get("type");

        holder.tvDescription.setText(desc != null ? desc : "No Description");

        if (Objects.equals(type, "INCOME")) {
            holder.tvAmount.setText(String.format(Locale.US, "+ %.2f ETB", amount));
            holder.tvAmount.setTextColor(Color.parseColor("#4CAF50")); // Green
        } else {
            holder.tvAmount.setText(String.format(Locale.US, "- %.2f ETB", amount));
            holder.tvAmount.setTextColor(Color.parseColor("#F44336")); // Red
        }
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDescription, tvAmount, tvDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}
