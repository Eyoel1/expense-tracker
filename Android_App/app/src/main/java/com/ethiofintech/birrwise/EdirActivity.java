package com.ethiofintech.birrwise;


import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Arrays;
import java.util.List;

public class EdirActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edir);

        String userId = getIntent().getStringExtra("USER_ID");
        RecyclerView recyclerView = findViewById(R.id.recyclerViewEdir);

        // 1. Define Ethiopian Months
        List<String> ethMonths = Arrays.asList(
                "Meskerem (Sep-Oct)", "Tikimt (Oct-Nov)", "Hidar (Nov-Dec)",
                "Tahisas (Dec-Jan)", "Tir (Jan-Feb)", "Yekatit (Feb-Mar)"
        );

        // 2. Setup the List Logic
        EdirAdapter adapter = new EdirAdapter(this, ethMonths, userId);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}