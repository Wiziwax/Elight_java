package com.chestnut_java.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chestnut_java.Entities.Category;
import com.chestnut_java.Adapters.CategoryAdapter;
import com.chestnut_java.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        // Set system bars colors
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // Set status bar color (top bar)
        window.setStatusBarColor(getResources().getColor(R.color.black));

        // Set navigation bar color (bottom system buttons)
        window.setNavigationBarColor(getResources().getColor(R.color.black));

        // Make the status bar icons dark (since we're using a light background)
        View decorView = window.getDecorView();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupCategoriesRecyclerView();


        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.navigation_home) {
                    // Handle home navigation
                    return true;
                } else if (itemId == R.id.navigation_activity) {
                    Intent intent = new Intent(HomeActivity.this, ActivityActivity.class);
                    startActivity(intent);
                    return true;}

                else if (itemId == R.id.navigation_map) {
                    Intent intent = new Intent(HomeActivity.this, LocationActivity.class);
                    startActivity(intent);
                    return true;

                } else if (itemId == R.id.navigation_profile) {
                    Intent intent = new Intent(HomeActivity.this, DeviceListActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        // Set default selected item
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);

        // Find the CardView by ID
        CardView mainCardMap = findViewById(R.id.mainCardMap);
        CardView bottomCard = findViewById(R.id.card2HomeActivity);

        ImageView cardImage = findViewById(R.id.cardImage);
//        Glide.with(this)
//                .load("https://example.com/image.jpg") // Replace with your image URL
//                .into(cardImage);


        // Set click listener
        mainCardMap.setOnClickListener(v -> {
            // Intent to navigate to another activity
            Intent intent = new Intent(HomeActivity.this, MarkerActivity.class);
            startActivity(intent);
        });

        bottomCard.setOnClickListener(v -> {
            // Intent to navigate to another activity
            Intent intent = new Intent(HomeActivity.this, RegisterDeviceActivity.class);
            startActivity(intent);
        });

    }
        private void setupCategoriesRecyclerView() {
//        RecyclerView categoriesRecyclerView = findViewById(R.id.categoriesRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        categoriesRecyclerView.setLayoutManager(layoutManager);

        // Sample categories data
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("Request Place", R.drawable.elight1));
        categories.add(new Category("New Places", R.drawable.elight2));
        categories.add(new Category("Request Inverter", R.drawable.elight3));
        categories.add(new Category("Other", R.drawable.elight4));
        categories.add(new Category("Food", R.drawable.elight5));
        categories.add(new Category("Travel", R.drawable.elight3));
        // Add more categories as needed

        CategoryAdapter adapter = new CategoryAdapter(this, categories);
//        categoriesRecyclerView.setAdapter(adapter);
    }
}