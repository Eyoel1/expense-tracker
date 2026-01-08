package com.ethiofintech.ethioexpensetracker.network;


import android.content.Context;
import android.util.Log;
import com.ethiofintech.ethioexpensetracker.data.local.AppDatabase;
import com.ethiofintech.ethioexpensetracker.data.local.LocalTransaction;
import com.ethiofintech.ethioexpensetracker.utils.SessionManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SyncManager {

    private Context context;
    private AppDatabase db;
    private SessionManager sessionManager;

    public SyncManager(Context context) {
        this.context = context;
        this.db = AppDatabase.getInstance(context);
        this.sessionManager = new SessionManager(context);
    }

    public void syncUnsyncedTransactions() {
        new Thread(() -> {
            // 1. Get all transactions where isSynced = false
            List<LocalTransaction> unsynced = db.transactionDao().getUnsyncedTransactions();

            if (unsynced.isEmpty()) return;

            Log.d("SyncManager", "Found " + unsynced.size() + " transactions to sync.");

            for (LocalTransaction lt : unsynced) {
                sendToServer(lt);
            }
        }).start();
    }

    private void sendToServer(LocalTransaction lt) {
        Map<String, Object> data = new HashMap<>();
        data.put("amount", lt.amount);
        data.put("description", lt.description);
        data.put("type", lt.type);
        data.put("userId", sessionManager.getUserId());

        // Send to Spring Boot
        ApiClient.getApiService().addTransaction(data).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // 2. If server says OK, update local Room to isSynced = true
                    new Thread(() -> {
                        lt.isSynced = true;
                        db.transactionDao().update(lt);
                        Log.d("SyncManager", "Synced: " + lt.description);
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("SyncManager", "Sync failed: " + t.getMessage());
            }
        });
    }
}