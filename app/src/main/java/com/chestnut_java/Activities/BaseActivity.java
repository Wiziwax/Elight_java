package com.chestnut_java.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.chestnut_java.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_base);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
                setupBottomNavigation();

        }
            private void setupBottomNavigation() {
                BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
                bottomNavigationView = findViewById(R.id.bottom_navigation);
                bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        int itemId = item.getItemId();

                        if (itemId == R.id.navigation_home) {
                            Intent intent = new Intent(BaseActivity.this, HomeActivity.class);
                            startActivity(intent);
                            return true;
                        } else if (itemId == R.id.navigation_activity) {
                            Intent intent = new Intent(BaseActivity.this, ActivityActivity.class);
                            startActivity(intent);
                            return true;}

                        else if (itemId == R.id.navigation_map) {
                            Intent intent = new Intent(BaseActivity.this, LocationActivity.class);
                            startActivity(intent);
                            return true;

                        } else if (itemId == R.id.navigation_profile) {
                            Intent intent = new Intent(BaseActivity.this, DeviceListActivity.class);
                            startActivity(intent);
                            return true;
                        }
                        return false;
                    }
                });

                // Set default selected item
                bottomNavigationView.setSelectedItemId(R.id.navigation_home);

            }


    }