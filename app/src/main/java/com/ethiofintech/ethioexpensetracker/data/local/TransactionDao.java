package com.ethiofintech.ethioexpensetracker.data.local;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface TransactionDao {
    @Insert
    void insert(LocalTransaction transaction);

    @Query("SELECT * FROM local_transactions WHERE isSynced = 0")
    List<LocalTransaction> getUnsyncedTransactions();

    @Update
    void update(LocalTransaction transaction);
}