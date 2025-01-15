package com.chestnut_java.Activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chestnut_java.Adapters.ActivityAdapter;
import com.chestnut_java.Entities.ActivityEntry;
import com.chestnut_java.R;

import java.util.ArrayList;
import java.util.List;

public class ActivityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<ActivityEntry> activities = new ArrayList<>();
        activities.add(new ActivityEntry("Activity 1", "2025-01-10 08:30", R.drawable.power, R.drawable.menu));
        activities.add(new ActivityEntry("Activity 2", "2025-01-05 14:15", R.drawable.power, R.drawable.menu));
        activities.add(new ActivityEntry("Activity 3", "2024-12-22 09:00", R.drawable.power, R.drawable.menu));

        ActivityAdapter adapter = new ActivityAdapter(activities);
        recyclerView.setAdapter(adapter);
    }
}