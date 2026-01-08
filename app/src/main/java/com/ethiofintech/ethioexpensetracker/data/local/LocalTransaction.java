package com.ethiofintech.ethioexpensetracker.data.local;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "local_transactions")
public class LocalTransaction {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public double amount;
    public String description;
    public String type; // INCOME or EXPENSE
    public boolean isSynced; // FALSE means it's only on the phone, needs to go to Spring Boot
}